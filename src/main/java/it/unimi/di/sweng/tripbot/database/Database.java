package it.unimi.di.sweng.tripbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	Connection conn;
	
	public Database(String url) throws SQLException{
		conn = DriverManager.getConnection(url);		
	}	
	
}
