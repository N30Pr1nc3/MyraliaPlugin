package de.myralia.akte.Commands;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.myralia.pluginManagement.MyraliaCommand;

public class Lc extends MyraliaCommand {

	public Lc() {
		this.setParams("lc",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		String user =""; 
		String name ="";
		if(args.length==0){
			if(sender instanceof Player){
				user = ((Player)sender).getUniqueId().toString();
				name = sender.getName();
			}else{
				sender.sendMessage("bitte usernamen angeben");
				return true;
			}    			
		}else if(args.length==1){
			OfflinePlayer u = Bukkit.getOfflinePlayer(args[0]);
       		if(u==null){
       			sender.sendMessage("username wurde nicht gefunden");
       			return true;
    		}           		
			user = u.getUniqueId().toString();
			name = u.getName();
		}
		
   		ArrayList<String> ret = de.myralia.pluginManagement.MyMySQLConnection.getAkte(user,"3");
   		sender.sendMessage(ChatColor.GREEN+"Grundstücke von '"+name+"'");
   		for (int i = 0 ; i< ret.size();i++){
   			sender.sendMessage(ret.get(i));
   		} 
		return true;
	}
}
