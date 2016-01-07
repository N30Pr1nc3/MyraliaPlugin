package de.myralia.akte.Commands;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.myralia.pluginManagement.MyraliaCommand;

public class LcAdd extends MyraliaCommand {

	public LcAdd() {
		this.setParams("lcadd",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		sender.sendMessage("Kommando abbruch melde dich bei neo");
		return true;
		
//		if(args.length != 3){
//			sender.sendMessage("Bitte gib einen user und den neuen lizenz namen und die anzahl blöcke an");
//			sender.sendMessage("/lcadd [user] [lcname] [blöcke]");
//			return true;
//		}
//		if(!NumberUtils.isNumber(args[2])){
//			sender.sendMessage("die anzahl der blöck muss eine Zahl sein");
//		};
//		Integer bloecke = Integer.parseInt(args[2]);
//		OfflinePlayer u = Bukkit.getOfflinePlayer(args[0]);
//       	if(u==null){
//       		sender.sendMessage("username wurde nicht gefunden");
//       		return true;
//    	}
//       	String euuid = "";
//       	if(sender instanceof Player){
//       		euuid = ((Player)sender).getUniqueId().toString();
//       	}else{
//       		euuid = "Console";
//       	}           	
//       	
//        Integer needetMoney = bloecke*25;
//        if(!Akte.economy.has(u, needetMoney)){
//        	sender.sendMessage("Der Spieler hat nicht genügend Taler er benötigt "+needetMoney);
//        	return true;
//        };
//        Akte.economy.withdrawPlayer(u, needetMoney);            
//       	MyMySQLConnection.addAkteneintrag(u.getUniqueId().toString(),euuid, args[1], bloecke, 3);
//    	sender.sendMessage("Neue Lizenz angelegt");          	
//		return true;
	}

}
