package it.unimi.di.sweng.tripbot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	Connection conn;
	Statement st;
	ResultSet rs;
	
	public Database(String url) throws SQLException{
		conn = DriverManager.getConnection(url);
		st = null;
		rs = null;		
	}	
	
	public ResultSet execQuery(String query) throws SQLException {
		st = conn.createStatement();

		if (query.toUpperCase().contains("SELECT"))
			rs = st.executeQuery(query);
		else
			//if(st.executeUpdate(query)==0) throw new SQLException();
			st.executeUpdate(query);
		
		return rs;
	}

	public void closeConnection() throws SQLException {
		rs.close();
		st.close();
		conn.close();
	}
	
}
