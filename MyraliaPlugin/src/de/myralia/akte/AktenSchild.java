package de.myralia.akte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Location;

import de.myralia.pluginManagement.MyMySQLConnection;
import de.myralia.pluginManagement.Myralia;


public class AktenSchild {

	private String msg ;
	private String userid;
	private String datum;
	private Integer akteneintrag;
	private Integer schildid;
	
	
	
	public AktenSchild(Integer id, Location location, String msg, int akteneintrag, String uuid, String datum){		
		this.msg = msg;
		this.akteneintrag = akteneintrag;
		this.datum = datum;
		this.userid = uuid;
		this.schildid = id;
		Myralia.Plugin.akte.addSchild(location,this);
	}
	
	public String getUserName() {
		AkteUser user = Myralia.Plugin.akte.getAkteUser(this.userid);
		if(user==null){
			return "";
		}
		return user.getName();		
	}

	public String getDatum() {
		return this.datum;
	}

	public Object getUUid() {
		return this.userid;
	}

	public String getAkteneintrag() {
		ResultSet eintrag = MyMySQLConnection.getAkte(this.akteneintrag);
		try {
			return eintrag.getString("text");
		} catch (SQLException e) {
			return "Es wurden keine Weiteren Anweisungen gefunden. Melde dich bei einem Supporter oder Moderator";
		}
		
	}

	public void delete(String userid) {
		MyMySQLConnection.editAktenSchildEintrag(this.schildid, "geloescht" , "'"+userid+"'");		
	}	
}
