package de.myralia.akte.Commands;

import org.bukkit.command.CommandSender;

import de.myralia.pluginManagement.Myralia;
import de.myralia.pluginManagement.MyraliaCommand;

public class PasswortSet extends MyraliaCommand {

	public PasswortSet() {
		this.setParams("passwortset",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length == 1){
			Myralia.Plugin.akte.newPasswort(args[0]);
			return true;
		}
		sender.sendMessage("Du musst schon ein neues Passwort angeben...");
		return true;
	}	
}


