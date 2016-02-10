package de.myralia.pluginManagement;

import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.myralia.akte.AktenSchild;

public class EventManager implements Listener {
	
	@EventHandler 
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		if(!Myralia.Plugin.akte.isFreigeschalten(e.getPlayer()))
		{
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Myralia.Plugin.akte.join(e.getPlayer());	
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Myralia.Plugin.akte.removePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerChatt(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String message = event.getMessage();		
		if(player==null){
			return;
		}
		if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(message,Myralia.Plugin.akte.getPasswort()))
		{
			event.setCancelled(true);
			if(!player.hasPermission("akte.freigeschaltet"))
			{
				player.sendMessage("Gratulation du weist das Passwort. Viel Spass auf dem Server");
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+player.getName()+" add akte.freigeschaltet");
				return;
			}
			else
			{
				player.sendMessage("Du wurdest bereits freigeschaltet. Bitte schreibe das Passwort nicht im Chat");
				return;
			}
		}
		
		if(!Myralia.Plugin.akte.isFreigeschalten(player))
		{			
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onSignChanged(SignChangeEvent Event) {				
		if(Event.getPlayer().hasPermission("akte.akteadd") && Event.getLine(0).startsWith("[akte]"))
		{
			if(!NumberUtils.isNumber(Event.getLine(2))){
				Event.getPlayer().sendMessage("die 3. Zeile muss die Id eines akteneintrages enthalten.");
				Event.setCancelled(true);
				return;
			}		
			int eintragId = Integer.parseInt(Event.getLine(2));
			
			Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}");		
			Matcher m = p.matcher(Event.getLine(3));
			if(!m.matches()){
				Event.setCancelled(true);			
				Event.getPlayer().sendMessage("Die 4. Zeile muss ein g�ltiges Datum im folgenden");
				Event.getPlayer().sendMessage("Vormat enthalten");
				Event.getPlayer().sendMessage("0000-00-00 00:00 [Jahr-Monat-Tag Stunde:Sekunde]");
				Event.getPlayer().sendMessage("angegeben wurde '"+Event.getLine(3)+"'");
				return;
			}			
			String datum = Event.getLine(3)+":00";
			
			ResultSet eintrag = MyMySQLConnection.getAkte(eintragId);
			if(eintrag == null){
				Event.getPlayer().sendMessage("Die Angegebene Aktenid konnte keinem Enitrag zugeordnet werden.");
				Event.setCancelled(true);
				return;			
			}	
			
			AktenSchild schild = Myralia.Plugin.akte.Create(Event.getBlock().getLocation(), Event.getLine(1),eintragId,eintrag, datum);
			if(Myralia.Plugin.akte.getSchild(Event.getBlock().getLocation())==null){
				Event.getPlayer().sendMessage("Etwas ist schief gelaufen. Das Schild konnte nicht angegeben werden bitte merk dir datum und uhrzeit und sag neo bescheit");
				Event.setCancelled(true);
				return;
			}

			Event.setLine(0, ChatColor.GREEN+Event.getLine(1));
			Event.setLine(1, ChatColor.BLACK+schild.getUserName());
			Event.setLine(2, ChatColor.BLACK+schild.getDatum());
			Event.setLine(3, "");

		}			
	}	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent Event) {
		if(!Event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		if(Event.getClickedBlock() == null)
		{
			return;
		}
		AktenSchild schild = Myralia.Plugin.akte.getSchild(Event.getClickedBlock().getLocation());
		if(schild==null)
		{
			return;
		}
		if(!Event.getPlayer().hasPermission("akte.akteadd") && !Event.getPlayer().getUniqueId().toString().equals(schild.getUUid()))
		{
			return;
		}
		Event.getPlayer().sendMessage(schild.getAkteneintrag());		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.WALL_SIGN)||e.getBlock().getType().equals(Material.SIGN_POST)){
			AktenSchild as = Myralia.Plugin.akte.getSchild((e.getBlock().getLocation()));
			if(as != null){
				if(e.getPlayer()==null){
					e.setCancelled(true);
					return;
				}
				if(!e.getPlayer().hasPermission("akte.akteadd")){
					e.setCancelled(true);
					return;
				}
				as.delete(e.getPlayer().getUniqueId().toString());
				return;
			}
		}
		Player player = e.getPlayer();
		if(player==null){
			return;
		}		
		if(!player.getWorld().getName().equals("myralia")){
			return;
		}	
		
		Material material = e.getBlock().getType();
		boolean doit = false;
		if(material.equals(material.DIAMOND_ORE)){doit = true;}
		if(material.equals(material.GOLD_ORE)){doit = true;}
		
		if(!doit){
			return;
		}		
		
		if(player.getGameMode().equals(GameMode.CREATIVE)){
			return;
		}
		
		Myralia.msgAnsTeam(ChatColor.GOLD+"Spieler "+player.getName()+" hat soeben "+material.toString()+" in Myralia abgebaut");		
	}		
}
