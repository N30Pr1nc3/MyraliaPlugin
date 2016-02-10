package de.myralia.PluginSqlConnector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SqlManager {
	public enum SQLConnectionType{
		Akte,
		Warper
	}
	
	private HashMap<SQLConnectionType, SqlConnection> connections;
	
	private SqlConnection getConnection(SQLConnectionType type){
		if(!this.connections.containsKey(type)){
			Class<?> c;
			try {
				c = Class.forName("de.myralia.PluginSqlConnector.SQLConnector"+type.name());
				Constructor<?> cons;
				cons = c.getConstructor(String.class);
				SqlConnection Connection = (SqlConnection) cons.newInstance(type);
				this.connections.put(type,Connection);
				return Connection;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
		return this.connections.get(type);
	}
}
