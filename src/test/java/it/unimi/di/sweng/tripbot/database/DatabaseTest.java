package it.unimi.di.sweng.tripbot.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.Configs;

public class DatabaseTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	@Test
	public void getConnectionOk() throws SQLException {
		Database db = new Database(Configs.INSTANCE.DB);
		assertTrue(db.conn.isValid(10));
	}

	@Test(expected = SQLException.class)
	public void getConnectionNo() throws SQLException {
		new Database("");
	}

	@Test
	public void execQuery() throws SQLException {
		Database db = new Database(Configs.INSTANCE.DB);
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
