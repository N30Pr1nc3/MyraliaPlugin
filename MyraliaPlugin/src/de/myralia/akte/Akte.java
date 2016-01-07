package de.myralia.akte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;


import org.bukkit.entity.Player;

import de.myralia.pluginManagement.Myralia;
import de.myralia.pluginManagement.MyMySQLConnection;
import de.myralia.pluginManagement.MyraliaPlugin;

public class Akte implements MyraliaPlugin {
	private HashMap<Location,AktenSchild> schilder;
	private HashMap<String,AkteUser> users;
	private File saveFile;
	private String passwort;
	
	
	@Override
	public boolean onEnable() {
		System.out.println("Lade Akten");
		this.users=new HashMap<String, AkteUser>();

		this.schilder = new HashMap<Location, AktenSchild>();	
		MyMySQLConnection.getSchilder();
		this.loadPasswort();
//		if(!this.setupEconomy()){
//			System.out.println("Vault Economy konnte nicht geladen werden");
//		};
		return true;
	}
	
	public AktenSchild Create(Location location, String msg, int akteneintrag, ResultSet resultset, String datum){
		Integer lschildid = MyMySQLConnection.createSign(akteneintrag, location.getBlockX(), location.getBlockY(), location.getBlockZ(), msg, location.getWorld().getName());
		String luserid ="";
		try {
			luserid = resultset.getString("uuid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		MyMySQLConnection.editAkteneintrag(akteneintrag, "wiedervorlage", "'"+datum+"'");
		
		return new AktenSchild(lschildid,location,msg,akteneintrag,luserid,datum);			 
	}
	
	public void join(Player p){
		AkteUser ap = Myralia.Plugin.akte.getAkteUser(p.getUniqueId().toString());
		
		if(ap.firstjoin==null){
			MyMySQLConnection.addAkteneintrag(p.getUniqueId().toString(), "Plugin", p.getName()+"_1", 40*40, 3);
			MyMySQLConnection.getUser(ap);			
		}
		MyMySQLConnection.sumOnlineTime(ap);
		ap.session = MyMySQLConnection.createSession(ap);
		
		if(p.hasPermission("akte.user")){
			return;
		}
		
		if(ap.getOnlineTime()>60*60*12){
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+p.getName()+" group add user");			
		}
		
		if(!ap.getName().equals(p.getName())){
			MyMySQLConnection.setUserValue(ap.getPlayerUUid(), "name" , "'"+p.getName()+"'");
			MyMySQLConnection.addAkteneintrag(ap.getPlayerUUid(),ap.getPlayerUUid(), p.getName(),0, 2);
		}	
	}
	
	public AktenSchild getSchild(Location location) {
		if(this.schilder.containsKey(location)){
			return this.schilder.get(location);
		}
		return null;
	}
	
	public AkteUser getAkteUser(String userid) {
		if(this.users.containsKey(userid)){
			return this.users.get(userid);
		}
		AkteUser ap = new AkteUser(userid);		
		MyMySQLConnection.getUser(ap);
		this.users.put(userid, ap);
		return ap;
	}
	
	public void loadPasswort(){
    	saveFile = new File(Myralia.Plugin.getDataFolder()+File.separator+File.separator+"passwort.txt");
	    BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(saveFile)));
			String l;
		    while((l=br.readLine()) != null){
		    	this.passwort = l;
		    }
		    br.close();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//again, make sure you have a file object	      	
    }
	
	public String getPasswort()
	{
		return this.passwort;
	}

	public void newPasswort(String passwort){
    	saveFile = new File(Myralia.Plugin.getDataFolder()+File.separator+File.separator+"passwort.txt");
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(saveFile,true)))) {
		    out.println(passwort);
    	    out.close();
    	    this.passwort = passwort;
		}catch (IOException e) {
		}	
    }
	
	public boolean isFreigeschalten(Player player) {
		if(!player.hasPermission("akte.freigeschaltet")){
			player.sendMessage("Lies Bitte die Regeln im Forum auf Myralia.de, dort findest");
			player.sendMessage("du ein Passwort mit dem du dich hier freischalten kannst.");
			player.sendMessage("Gib es einfach hier im Chat ein.");
			return false;
		};	
		return true;
	}

	public void addSchild(Location location, AktenSchild aktenSchild) {
		this.schilder.put(location, aktenSchild);		
	}

	
	public void removePlayer(Player player) {
		if(this.users.containsKey(player)){
			this.users.get(player).closeSession();
		}
		this.users.remove(player);	
	}
	
	
}
