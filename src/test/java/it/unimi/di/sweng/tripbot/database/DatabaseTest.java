package it.unimi.di.sweng.tripbot.database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class DatabaseTest {
	private static String url;

	@BeforeClass
	public static void init() {
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

	@Test
	public void execQuery() throws SQLException {
		Database db = new Database(url);
		db.execQuery("CREATE TABLE test (poi varchar(20));");
		db.execQuery("INSERT INTO test (poi) VALUES ('Duomo di Milano');");

		ResultSet poiElem = db.execQuery("SELECT poi FROM test;");
		poiElem.next();
		assertEquals("Duomo di Milano", poiElem.getString(1));

		db.execQuery("DROP table test");

		poiElem.close();
		db.closeConnection();

	}

}
