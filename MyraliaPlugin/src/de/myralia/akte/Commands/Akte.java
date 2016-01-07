package de.myralia.akte.Commands;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import de.myralia.pluginManagement.MyraliaCommand;

public class Akte extends MyraliaCommand {

	public Akte() {
		this.setParams("akte",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length!=1){
			sender.sendMessage("Bitte gib den Usernamen an");
			return true;
		}
		OfflinePlayer user = Bukkit.getOfflinePlayer(args[0]);
   		if(user==null){
   			sender.sendMessage("username wurde nicht gefunden");
   			return true;
		}
   		ArrayList<String> ret = de.myralia.pluginManagement.MyMySQLConnection.getAkte(user.getUniqueId().toString(),"");
   		sender.sendMessage(ChatColor.GREEN+"Akte von '"+user.getName()+"'");
   		for (int i = 0 ; i< ret.size();i++){
   			sender.sendMessage(ret.get(i));
   		} 
		return true;
	}

}
