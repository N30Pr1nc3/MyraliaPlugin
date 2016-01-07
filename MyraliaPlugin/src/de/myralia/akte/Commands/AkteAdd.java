package de.myralia.akte.Commands;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.myralia.pluginManagement.MyraliaCommand;

public class AkteAdd extends MyraliaCommand {

	public AkteAdd() {
		this.setParams("akteadd",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length < 2){
			sender.sendMessage("Bitte gib den Usernamen an und eine Message an");
			return false;
		}
		OfflinePlayer user = Bukkit.getOfflinePlayer(args[0]);
   		if(user==null){
   			sender.sendMessage("username wurde nicht gefunden");
   			return false;
		}
   		
		String Msg ="";
		for (int i = 1; i < args.length; i++){
			Msg = Msg+" "+args[i];
		}
		
		if(sender instanceof Player){
			de.myralia.pluginManagement.MyMySQLConnection.addAkteneintrag(user.getUniqueId().toString(), ((Player) sender).getUniqueId().toString() , Msg,0, 1);
		}else{
			de.myralia.pluginManagement.MyMySQLConnection.addAkteneintrag(user.getUniqueId().toString(), "Console" , Msg,0, 1);
		}
		sender.sendMessage("Akteneintrag wurde angelegt");
		return true;
	}

}
