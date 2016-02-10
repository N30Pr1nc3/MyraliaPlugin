package de.myralia.pluginManagement;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.myralia.akte.Akte;

public class Myralia extends JavaPlugin{
	public static Myralia Plugin;
	private HashMap<String, MyraliaCommand> commands;
	private EventManager eventManager;
	public Akte akte;
	
	
	public void onEnable()
	{			
		Myralia.out("Lade Myralia Plugin Sammlung");
		Myralia.Plugin = this;
		Myralia.out("Lade EventHAndler");
		this.eventManager = new EventManager();
		getServer().getPluginManager().registerEvents(this.eventManager, this);
		
		Myralia.out("Initialisiere Command Liste");
		this.commands = new HashMap<String, MyraliaCommand>();
		this.addCommand(new de.myralia.pluginManagement.GeneralCommands.Version());
		Myralia.out("Lade subPlugin Akte");
		this.akte = new Akte();
		this.akte.onEnable();
		
	}  
	
	public boolean addCommand(MyraliaCommand command)
	{
		if(this.commands.containsKey(command.getName()))
		{
			Myralia.out("Fehler 1: Es wurde Versucht das Kommando "+command.getName()+"doppelt zu erstellen");
			return false;
		}
		else
		{
			this.commands.put(command.getName(), command);			
			Myralia.out("Lade Kommando: "+command.getName());
		}
		return true;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		for(String commandName : this.commands.keySet())
		{
			if(!cmd.getName().equals(commandName))
			{
				continue;
			}
			MyraliaCommand command = this.commands.get(commandName);
			if(command.isConsoleCommand())
			{
				if(sender instanceof ConsoleCommandSender)
				{
					return command.onCommand(sender, args);	
				}
				else
				{
					sender.sendMessage("Dieses Kommando kann von der Konsole aus nicht verwendet werden.");
				}				
			}
			else if(command.isHumanCommand())
			{
				if(sender instanceof HumanEntity)
				{
					return command.onCommand(sender, args);	
				}
				else
				{
					sender.sendMessage("Dieses Kommando kann aus dem Spiel heraus nicht verwendet werden.");
				}				
			}
			Myralia.out("Fehler 2: Offensichtlich ist der Commandsender \""+sender.getClass()+"\" nicht m�glich");
			return false;		
		}
		
		sender.sendMessage("Das komando konnte leider nicht gefunden werden bitte wende dich an einen Administrator");
		return true;
	}
	    
	
	public static void out(String text)
	{
		System.out.println(text);
	}
	
	public static void msgAnsTeam(String[]  msg) {
		for(Player maybeteam : Bukkit.getOnlinePlayers()) {			
			if(maybeteam.hasPermission("akte.add")){
				maybeteam.sendMessage(msg);				
			}
		}		
	}
	
	public static void msgAnsTeam(String string) {
		String[] msg = {string};
		Myralia.msgAnsTeam(msg);
		
	}

}
