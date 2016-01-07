package de.myralia.akte.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.myralia.pluginManagement.MyraliaCommand;

public class AkteTp extends MyraliaCommand {

	public AkteTp() {
		this.setParams("aktetp",null,false,true);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(args.length!=1){
			sender.sendMessage("Bitte gib eine Eintragsnummer an");
			return true;
		}
		if(!NumberUtils.isNumber(args[0])){
			sender.sendMessage("Bitte gib eine zahl als Akteneintragsnummer an");
			return true;
		}
		ResultSet row = de.myralia.pluginManagement.MyMySQLConnection.getAkte(Integer.parseInt(args[0]));
		if (row==null){
			sender.sendMessage("Leider konnte zu diesem Akteneintrag kein warppunkt gefunden werden (2).");    			
			return true;
		}
		
		String worldname = null;
		Integer z = 0;
		Integer y = 0;
		Integer x = 0;
		try {
			x  = row.getInt("schild_x");
			y  = row.getInt("schild_y");
			z  = row.getInt("schild_z");
    		worldname = row.getString("schild_world");	    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(worldname==null){
			try {
				x  = row.getInt("region_min_x");
				y  = row.getInt("region_min_y");
				z  = row.getInt("region_min_z");
	    		worldname = row.getString("schild_world");	    		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(worldname==null){
			sender.sendMessage("Leider konnte zu diesem Akteneintrag kein warppunkt gefunden werden (1).");    			
			return true;
		}
		World world = Bukkit.getWorld(worldname);
		if(world == null){
			sender.sendMessage("das ziel liegt in einder welt die es nicht gibt '"+worldname+"' ");
		}
		Location l = new Location(world, x, y, z);
		((Player)sender).teleport(l);
		
		
		return true;
	}

}
