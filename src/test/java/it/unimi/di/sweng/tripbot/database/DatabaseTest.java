package it.unimi.di.sweng.tripbot.database;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class DatabaseTest {
	private static String url;
	
	@BeforeClass
	public static void init(){
		url = System.getenv("JDBC_DATABASE_URL");
	}
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	@Test
	public void getConnectionOk() throws SQLException {
			Database db = new Database(url);
			assertTrue(db.conn.isValid(10));
	}

	@Test(expected = SQLException.class)
	public void getConnectionNo() throws SQLException {
		new Database("");

	}

}
