package it.unimi.di.sweng.tripbot.database;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class DatabaseTest {

	String url = "jdbc:postgresql://ec2-54-243-201-116.compute-1.amazonaws.com:5432/deidsdkc3b51iu?user=txwssngaqdputz&password=Ehxl5_SVhHsS-f8RNbd1aUXDqL&sslmode=require";

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	@Test
	public void getConnectionOk() throws SQLException {
		try {
			Database db = new Database(url);
			assertTrue(db.conn.isValid(10));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(expected = SQLException.class)
	public void getConnectionNo() throws SQLException {

		new Database("");

	}

}
