package it.unimi.di.sweng.tripbot.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class DatabaseTest {
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	Database db;
	@Before
	public void init () 
		{
			 db =  new Database();
		}
	
	@Test
	public void getConnectionOk() throws SQLException{
		String url = "jdbc:postgresql://ec2-54-243-201-116.compute-1.amazonaws.com:5432/deidsdkc3b51iu?user=txwssngaqdputz&password=Ehxl5_SVhHsS-f8RNbd1aUXDqL&sslmode=require";
		try {
			Connection conn = db.connect(url);
			assertTrue(conn.isValid(10));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = SQLException.class)
	public void getConnectionNo() throws SQLException{
		String url = "";
		db.connect(url);
		
	}
	
}
