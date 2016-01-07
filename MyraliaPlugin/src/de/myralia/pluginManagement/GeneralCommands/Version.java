package de.myralia.pluginManagement.GeneralCommands;

import org.bukkit.command.CommandSender;

public class Version extends de.myralia.pluginManagement.MyraliaCommand{

	public Version() {
		this.setParams("Version",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		sender.sendMessage("Myralia Version 0.1");
		return true;
	}

}
