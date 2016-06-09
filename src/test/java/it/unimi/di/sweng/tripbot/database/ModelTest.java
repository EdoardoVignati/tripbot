package it.unimi.di.sweng.tripbot.database;

import java.util.Date;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.Configs;
import it.unimi.di.sweng.tripbot.PointOfInterest;
import it.unimi.di.sweng.tripbot.Geolocalization.APosition;
import it.unimi.di.sweng.tripbot.Geolocalization.LocationProvider;

public class ModelTest {
	static SimpleDateFormat dateFormat;
	static Database db;
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);
	
	@BeforeClass
	public static void globalSetUp() throws ParseException, SQLException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		db = new Database(System.getenv("JDBC_DATABASE_URL"));
	}
	
	@Test
	public void insertPOITest() throws Exception {		
		String name = "Duomo", groupId = "123";
		Date meetDate = dateFormat.parse("2016-06-03 10:00:00");
		APosition position = (new LocationProvider().getPositionByName("Piazza Duomo Milano"));		
		PointOfInterest p1 = new PointOfInterest(name, meetDate, position, groupId);
		Model model = new Model();
		model.insertNewPointOfInterest(p1);
		
		ResultSet rs = db.execQuery("SELECT * FROM trips WHERE chat_id='123';");
		if(rs.next()){
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
		Model model = new Model();
		model.insertNewPointOfInterest(p1);
		PointOfInterest p2 = model.getPointOfInterest("123", "Duomo");
		
		assertEquals(p1.name, p2.name);
		assertEquals(p1.groupId, p2.groupId);
		assertEquals(p1.position.toString(), p2.position.toString());
		assertEquals(p1.meetDate, p2.meetDate);
		db.execQuery("DELETE FROM trips WHERE chat_id='123' AND poi='Duomo';");
	}
	
	@Test
	public void removePOITest() throws Exception {	
		String name = "Duomo", groupId = "123";
		Date meetDate = dateFormat.parse("2016-06-03 10:00:00");
		APosition position = (new LocationProvider().getPositionByName("Piazza Duomo Milano"));		
		PointOfInterest p1 = new PointOfInterest(name, meetDate, position, groupId);
		Model model = new Model();
		model.insertNewPointOfInterest(p1);
		model.removePointOfInterest("123", "Duomo");
		
		assertNull(model.getPointOfInterest("123", "Duomo"));
	}
}
