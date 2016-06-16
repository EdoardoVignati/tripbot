package it.unimi.di.sweng.tripbot.database;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.geolocalization.APosition;
import it.unimi.di.sweng.tripbot.geolocalization.LocationProvider;
import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;
import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterestDateComparator;
import it.unimi.di.sweng.tripbot.server.Configs;

public class ModelTest {
	static SimpleDateFormat dateFormat;
	static Database db;

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	@BeforeClass
	public static void globalSetUp() throws ParseException, SQLException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		db = new Database(Configs.INSTANCE.DB);
	}

	@Test
	public void insertPOITest() throws Exception {
		String name = "Duomo", groupId = "123";
		Date meetDate = dateFormat.parse("2016-06-03 10:00:00");
		APosition position = (new LocationProvider().getPositionByName("Piazza Duomo Milano"));
		PointOfInterest p1 = new PointOfInterest(name, meetDate, position, groupId);
		DatabaseModel model = new DatabaseModel();
		model.insertNewPointOfInterest(p1);

		ResultSet rs = db.execQuery("SELECT * FROM trips WHERE chat_id='123';");
		if (rs.next()) {
			assertEquals("Duomo", rs.getString("poi"));
			assertEquals("2016-06-03 10:00:00", rs.getString("meet_date"));
			assertTrue(rs.getString("address").contains("Piazza del Duomo"));
			db.execQuery("DELETE FROM trips WHERE chat_id='123' AND poi='Duomo';");
			rs.close();
		}
	}

	@Test
	public void getPOITest() throws Exception {
		String name = "Duomo", groupId = "123";
		Date meetDate = dateFormat.parse("2016-06-03 10:00:00");
		APosition position = (new LocationProvider().getPositionByName("Piazza Duomo Milano"));
		PointOfInterest p1 = new PointOfInterest(name, meetDate, position, groupId);
		DatabaseModel model = new DatabaseModel();
		model.insertNewPointOfInterest(p1);
		PointOfInterest p2 = model.getPointOfInterest("123", "Duomo");

		assertEquals(p1.name, p2.name);
		assertEquals(p1.groupId, p2.groupId);
		assertEquals(p1.position.toString(), p2.position.toString());
		assertEquals(p1.meetDate, p2.meetDate);
		db.execQuery("DELETE FROM trips WHERE chat_id=123 AND poi='Duomo';");
	}

	@Test
	public void removePOITest() throws Exception {
		String name = "Duomo", groupId = "123";
		Date meetDate = dateFormat.parse("2016-06-03 10:00:00");
		APosition position = (new LocationProvider().getPositionByName("Piazza Duomo Milano"));
		PointOfInterest p1 = new PointOfInterest(name, meetDate, position, groupId);
		DatabaseModel model = new DatabaseModel();
		model.insertNewPointOfInterest(p1);
		model.removePointOfInterest(p1);
		model.removePointOfInterest(p1);

		assertNull(model.getPointOfInterest("123", "Duomo"));

	}

	@Test
	public void getListPOITest() throws Exception {
		DatabaseModel model = new DatabaseModel();
		List<PointOfInterest> pointList;

		Date meetDate = dateFormat.parse("2016-06-03 10:00:00");
		APosition position = (new LocationProvider().getPositionByName("Piazza Duomo Milano"));
		PointOfInterest p1 = new PointOfInterest("Duomo", meetDate, position, "123");
		model.insertNewPointOfInterest(p1);

		meetDate = dateFormat.parse("2016-06-04 10:00:00");
		position = (new LocationProvider().getPositionByName("Piazza San Babila Milano"));
		PointOfInterest p2 = new PointOfInterest("San Babila", meetDate, position, "123");
		model.insertNewPointOfInterest(p2);

		meetDate = dateFormat.parse("2016-06-05 10:00:00");
		position = (new LocationProvider().getPositionByName("Piazzale Loreto Milano"));
		PointOfInterest p3 = new PointOfInterest("Loreto", meetDate, position, "123");
		model.insertNewPointOfInterest(p3);

		pointList = model.getPointOfInterestList("123");
		final PointOfInterestDateComparator myComparator = new PointOfInterestDateComparator();
		Collections.sort(pointList, myComparator);

		assertEquals(pointList.get(0).name, "Duomo");
		assertEquals(pointList.get(1).name, "San Babila");
		assertEquals(pointList.get(2).name, "Loreto");

		model.removePointOfInterest(p1);
		model.removePointOfInterest(p2);
		model.removePointOfInterest(p3);

	}
}
