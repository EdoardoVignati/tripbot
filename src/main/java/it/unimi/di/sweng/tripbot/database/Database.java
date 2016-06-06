package it.unimi.di.sweng.tripbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
		
	public Connection connect(String url) throws SQLException{
		Connection conn = DriverManager.getConnection(url);
		return conn;
		
	}	

}
