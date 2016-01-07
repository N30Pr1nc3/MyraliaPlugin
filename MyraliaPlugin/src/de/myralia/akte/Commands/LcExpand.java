package de.myralia.akte.Commands;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import de.myralia.pluginManagement.MyraliaCommand;

public class LcExpand extends MyraliaCommand {
	public LcExpand(){
		this.setParams("lcexpand",null,true,true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		sender.sendMessage("Kommando abbruch melde dich bei neo");
		return true;
//		if(args.length != 3){
//			sender.sendMessage("Bitte gib einen user und den neuen lizenz namen und die anzahl blöcke an");
//			sender.sendMessage("/lcexpand [Spieler] [lizenznummer] [blöcke]");
//			return true;
//		}
//		if(!NumberUtils.isNumber(args[1])){
//			sender.sendMessage("die Lizenznummer muss eine Zahl sein");
//		};
//		Integer lcnummer = Integer.parseInt(args[1]);
//		if(!NumberUtils.isNumber(args[2])){
//			sender.sendMessage("die Anzahl der Blöcke muss eine Zahl sein");
//		};
//		Integer bloecke = Integer.parseInt(args[2]);
//		OfflinePlayer u = Bukkit.getOfflinePlayer(args[0]);
//       	if(u==null){
//       		sender.sendMessage("username wurde nicht gefunden");
//       		return true;
//    	} 	
//       	
//        Integer needetMoney = bloecke*25;
//        if(!Akte.economy.has(u, needetMoney)){
//        	sender.sendMessage("Der Spieler hat nicht genügend Taler er benötigt "+needetMoney);
//        	return true;
//        };
//        Akte.economy.withdrawPlayer(u, needetMoney);            
//       	MyMySQLConnection.editAkteneintrag(lcnummer,"wert"," wert + '"+bloecke+"'");
//    	sender.sendMessage("Lizenz Erweitert");          	
//		return true;
	}

}
