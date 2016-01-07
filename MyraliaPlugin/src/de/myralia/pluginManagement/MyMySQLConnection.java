package de.myralia.pluginManagement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.myralia.akte.AkteUser;
import de.myralia.akte.AktenSchild;
import net.md_5.bungee.api.ChatColor;
 
public class MyMySQLConnection{
private static Connection con = null;
//private static String dbHost = "localhost"; // Hostname
private static String dbHost = "127.0.0.1"; // Hostname
private static String dbPort = "3306";      // Port -- Standard: 3306
private static String dbName = "minecraft_akte";   // Datenbankname
private static String dbUser = "minecraft_akte";     // Datenbankuser
private static String dbPass = "93JQ3jR6pEhjQ9nRHsW5jtPzpynnVcnU";      // Datenbankpasswort
 
	private MyMySQLConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden. 
        		// Verbindung zur JDBC-Datenbank herstellen.
        	con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+dbName+"?"+"user="+dbUser+"&"+"password="+dbPass);
		} catch (ClassNotFoundException e) {
    		System.out.println("Treiber nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moglich");
        	System.out.println("SQLException: " + e.getMessage());
        	System.out.println("SQLState: " + e.getSQLState());
        	System.out.println("VendorError: " + e.getErrorCode());
		}
	}
 
	private static Connection getInstance(){
		if(con == null){
	        new MyMySQLConnection();
	    }
	    try {
			if(con.isClosed()){
			    new MyMySQLConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return con;
	}

	public static void getUser(AkteUser user){
		con = getInstance();      
		if(con != null){
		//Abfrage-Statement erzeugen.
			Statement query;
	     
			try {
				query = con.createStatement();
				String sql ="SELECT * FROM users where uuid = '"+user.getPlayerUUid()+"'";
				ResultSet result = query.executeQuery(sql);
				if (result.next()) {
					user.firstjoin =  result.getTimestamp("firstjoin");
					user.name= result.getString("name");
				}else{
					sql = "insert into users set name='"+user.getName()+"',  uuid ='"+user.getPlayerUUid()+"', firstjoin = now()";
					query.execute(sql);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setUserValue(String uuid, String key, String value) {
		System.out.println(uuid);
		System.out.println(key);
		System.out.println(value);
		con = getInstance();

		if(con != null){
			Statement query;		
			try {
			    query = con.createStatement();
			    String sql =  "update users set "+key+" = "+value+" where uuid ='"+uuid+"' ";
			    query.execute(sql);
			} catch (SQLException e) {
			  e.printStackTrace();
			} 
		}	
	}
	
	public static void editAktenSchildEintrag(Integer id, String key, String value) {
		System.out.println(id);
		System.out.println(key);
		System.out.println(value);
		con = getInstance();

		if(con != null){
			Statement query;		
			try {
			    query = con.createStatement();
			    String sql =  "update akten_schild set "+key+" = "+value+" where id ='"+id+"' ";
			    query.execute(sql);
			} catch (SQLException e) {
			  e.printStackTrace();
			} 
		}	
	}
	
	public static void sumOnlineTime(AkteUser user) {
		con = getInstance();
	    
	    if(con != null){
	    // Abfrage-Statement erzeugen.
	    Statement query;
	   
	    try {
	        query = con.createStatement();
	        String sql ="SELECT sum(dauer) as dauer FROM users_sessions where uuid = '"+user.getPlayerUUid()+"'";
	        ResultSet result = query.executeQuery(sql);
	        while (result.next()) {
	      	  user.addPlaytime(result.getFloat("dauer"));
	        }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
		
	}

	public static Integer createSession(AkteUser user) {
		con = getInstance();
	    
	    if(con == null){
	    	return -1;
	    }
	    // Abfrage-Statement erzeugen.
	    try{
	    	Statement query;
	    	query = con.createStatement();
	        String sql ="insert into users_sessions set ";
		    sql = sql +  " uuid = '"+user.getPlayerUUid()+"', ";
		    sql = sql +  " start= now(), ";		    
		    sql = sql +  " dauer= '0' ";        
		    
	        query.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        Integer result = -1;
	        ResultSet rs = query.getGeneratedKeys();
	        if (rs.next()){
	            result=rs.getInt(1);
	        }
	        rs.close();		    
		    return result;
	        
	        //return query.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return -1;
	}
	
	public static Integer createSign(Integer akteneintrag, Integer x, Integer y, Integer z, String msg,String world) {
		con = getInstance();
	    
	    if(con == null){
	    	return -1;
	    }
	    // Abfrage-Statement erzeugen.
	    try{
	    	
	    	
	    	
	    	
	    	
	    	
	    	Statement query;
	    	query = con.createStatement();
	    	String sql ="insert akten_schild set ";
		    sql = sql +  " x = '"+x+"', ";
		    sql = sql +  " y = '"+y+"', ";
		    sql = sql +  " z = '"+z+"', ";
		    sql = sql +  " world = '"+world+"', ";
		    sql = sql +  " msg = '"+msg+"', ";
		    sql = sql +  " akteneintrag = '"+akteneintrag+"', ";
		    sql = sql +  " geloescht= '';"; 
		    
	        query.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
	        Integer result = -1;
	        ResultSet rs = query.getGeneratedKeys();
	        if (rs.next()){
	            result=rs.getInt(1);
	        }
	        rs.close();		    
		    return result;        
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return -1;
	}
	
	public static void updateSession(AkteUser user) {
		con = getInstance();	    
	    if(con == null){
	    	return;
	    }
	    // Abfrage-Statement erzeugen.
	    try{
	    	Statement query;
	    	query = con.createStatement();
	        String sql ="update users_sessions set ";
		    sql = sql +  " dauer= now()-start ";        
		    sql = sql +  " where id = '"+user.session.toString()+"' ";
		    //System.out.println(sql);
	        query.executeUpdate(sql);
	        
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return;
	}

	public static boolean addAkteneintrag(String user,String euser,String msg,Integer wert, Integer typ  ){
		con = getInstance();	    
	    if(con == null){
	    	return false;
	    }
	    if(user==null || euser==null){
	    	return false;
	    }
	    // Abfrage-Statement erzeugen.
	    try{
	    	Statement query;
	    	query = con.createStatement();
	        String sql ="insert into users_akte set ";        
		    sql = sql +  " uuid = '"+ user +"', ";
		    sql = sql +  " euuid = '"+ euser +"', ";
		    sql = sql +  " text = '"+ msg +"', ";
		    sql = sql +  " wert = '"+ wert.toString() +"', ";
		    sql = sql +  " typ = '"+ typ.toString() +"', ";
		    sql = sql +  " timestamp = now(), ";
		    sql = sql +  " geloescht = '' ";
		    //System.out.println(sql);
	        query.executeUpdate(sql);
	        
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return true;
	}

	public static ArrayList<String> getAkte(String user,String type){
		ArrayList<String> ret = new ArrayList<String>();
		con = getInstance();
	    
	    if(con != null){
	    	Statement query;
	   
		    try {
		        query = con.createStatement();
		        String sql ="select "; 
		        sql = sql+"s.anzeige, ";  
		        sql = sql+"a.id, "; 
		        sql = sql+"concat(a.text,if(s.id=3,concat('\n"+ChatColor.GRAY+"Blöcke: "+ChatColor.BLUE+"',a.wert,'"+ChatColor.WHITE+"'),''),if(isnull(min_x) or isnull(min_z) or isnull(max_z) or isnull(max_z),'','"+ChatColor.GRAY+"\nProtection: "+ChatColor.BLUE+"ja"+ChatColor.WHITE+"')) as text, "; //concat('\nProtection xy',min_x,':',min_z,'-',max_x,':',max_z,' (',(max_x-min_x),'x',(max_z-min_z),'=',(max_x-min_x)*(max_z-min_z),')'))) as text, "; 
		        sql = sql+"a.timestamp, ";
		        sql = sql+"a.wert, ";
		        sql = sql+"u.name as user, "; 
		        sql = sql+"eu.name as euser "; 
		        sql = sql+"from users_akte as a "; 
		        sql = sql+"join users as u on a.uuid = u.uuid "; 
		        sql = sql+"join users as eu on a. euuid = eu.uuid ";
		        sql = sql+"join eintrag_typen as s on a.typ = s.id ";
		        sql = sql+"left join minecraft_wg.myralia_region_cuboid as r on a.typ = 3 and upper(r.region_id) = upper(a.text) ";
		        sql = sql+"where geloescht = '' ";
		        sql = sql+"and a.uuid = '"+user+"' ";
		        //System.out.println(sql);
		        if(!type.equals("")){
		        	sql = sql+"and s.id='"+type+"' ";
		        }
		        sql = sql+"order by s.sort,a.timestamp;";
		        ResultSet result = query.executeQuery(sql);
		        while (result.next()) {		        
		        	String eintrag = ChatColor.GREEN + ((Integer)result.getInt("id")).toString()+ChatColor.WHITE+" - ";
		        	eintrag = eintrag+result.getString("timestamp").substring(0, 10);
		        	eintrag = eintrag+" ("+result.getString("euser")+") ";
		        	eintrag = eintrag+result.getString("anzeige")+" ";
		        	eintrag = eintrag+result.getString("text");
		        	ret.add(eintrag);
		        }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
	    }		
		return ret;
	}

	public static ResultSet getAkte(int akteneintrag) {
		con = getInstance();	    
	    if(con != null){
	    	Statement query;	   
		    try {
		        query = con.createStatement();
		        String sql ="select "; 
		        sql = sql + "	a.id, "; 
		        sql = sql + "	a.uuid, "; 
		        sql = sql + "	a.euuid, "; 
		        sql = sql + "	a.text, "; 
		        sql = sql + "	a.wert, "; 
		        sql = sql + "	a.typ, "; 
		        sql = sql + "	a.wiedervorlage, "; 
		        sql = sql + "	a.timestamp, "; 
		        sql = sql + "	a.geloescht as akte_geloescht, "; 
		        sql = sql + "	s.id as schild_id, "; 
		        sql = sql + "	s.x as schild_x, "; 
		        sql = sql + "	s.y as schild_y, "; 
		        sql = sql + "	s.z as schild_z, "; 
		        sql = sql + "	s.world as schild_world, "; 
		        sql = sql + "	s.msg as schild_msg, "; 
		        sql = sql + "	s.geloescht as schild_geloescht, "; 
		        sql = sql + "	r.region_id, "; 
		        sql = sql + "	r.world_id as region_world, "; 
		        sql = sql + "	r.min_x as region_min_x, "; 
		        sql = sql + "	r.min_y as region_min_y, "; 
		        sql = sql + "	r.min_z as region_min_z, "; 
		        sql = sql + "	r.max_x as region_max_x, "; 
		        sql = sql + "	r.max_y as region_max_y, "; 
		        sql = sql + "	r.max_z as region_max_z "; 
		        sql = sql + "from users_akte as a "; 
		        sql = sql + "left join akten_schild as s on a.id = s.akteneintrag "; 
		        sql = sql + "left join minecraft_wg.myralia_region_cuboid as r on a.typ = 3 and upper(r.region_id) = upper(a.text) "; 
		        sql = sql + "where a.id = '"+akteneintrag+"' ";
		        //System.out.println(sql);
		        ResultSet result = query.executeQuery(sql);
		        if (result.next()) {		        
		        	return result;
		        }else{
		        	return null;
		        }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
	    }		
		return null;
		
	}
	
	public static Boolean editAkteneintrag(Integer eintragsnummer, String key, String value) {
		con = getInstance();	    
	    if(con == null){
	    	return false;
	    }
	    // Abfrage-Statement erzeugen.
	    try{
	    	Statement query;
	    	query = con.createStatement();
	        String sql ="update users_akte set "+key+ " = "+value+" where id = '"+eintragsnummer+"'";
		    System.out.println(sql);
	        query.executeUpdate(sql);
	        
	    } catch (SQLException e) {
	      e.printStackTrace();
	      return false;
	    }
	    return true;
		
	}
	
	public static void getSchilder() {
		con = getInstance();
	    
	    if(con != null){
	    // Abfrage-Statement erzeugen.
	    Statement query;
	   
	    try {
	        query = con.createStatement();
	        String sql ="Select s.*,ua.uuid,ua.wiedervorlage "; 
	        sql = sql + "from akten_schild as s ";
	        sql = sql + "join users_akte as ua on s.akteneintrag = ua.id ";
	        sql = sql + "where s.geloescht = '' ";
	        ResultSet result = query.executeQuery(sql);
	        while (result.next()) {
	        	Location l = new Location(Bukkit.getWorld(result.getString("world")), result.getInt("x"), result.getInt("y"), result.getInt("z"));
	        	new AktenSchild(result.getInt("id"),l, result.getString("msg"), result.getInt("akteneintrag"), result.getString("uuid"), result.getString("wiedervorlage"));	        		        	
	        }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	}
		
		
		
	}	
}

