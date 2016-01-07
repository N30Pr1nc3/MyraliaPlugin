package de.myralia.akte.Commands;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.myralia.pluginManagement.MyraliaCommand;

public class AkteDel extends MyraliaCommand {

	public AkteDel() {
		this.setParams("aktedel",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length!=1){
			sender.sendMessage("die nummer des eintrages an den du löschen möchtest");
			return true;
		}
		if(!NumberUtils.isNumber(args[0])){
			sender.sendMessage("Nummer des Eintrages muss eine Zahl sein");
			return true;
		};
		String sendername = "";
	    if(sender instanceof Player){
	    	sendername = ((Player) sender).getUniqueId().toString();
	    }else{
	    	sendername= "Console";
	    }    		    		
		de.myralia.pluginManagement.MyMySQLConnection.editAkteneintrag(Integer.parseInt(args[0]), "geloescht", "'"+sendername+"'");    		 
		return true;
	}

}
