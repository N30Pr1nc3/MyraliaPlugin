package de.myralia.akte;

import java.sql.Timestamp;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.myralia.pluginManagement.MyMySQLConnection;
import de.myralia.pluginManagement.Myralia;

public class AkteUser {
	private String uuid = null;
	public Timestamp firstjoin =null;
	private long onlinetime =0;
	public Integer session = null;
	public String name;	
	
	AkteUser(String uuid){
		this.uuid=uuid;
	}	
	
	public void closeSession(){
		MyMySQLConnection.updateSession(this);		
	}

	public int getOnlineTime(){	
		return (int) (this.onlinetime);
	}
	
	public String getPlayerUUid() {
		return this.uuid;
	}

	public String getName(){
		return this.name;
	}
	
	public void addPlaytime(float playtime) {
		System.out.println(playtime);
		System.out.println(this.onlinetime);		
		this.onlinetime += playtime;
	}	
}
