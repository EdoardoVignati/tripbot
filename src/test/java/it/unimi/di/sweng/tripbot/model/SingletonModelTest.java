package it.unimi.di.sweng.tripbot.model;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import it.unimi.di.sweng.tripbot.GMapsPosition;
import it.unimi.di.sweng.tripbot.PointOfInterest;

public class SingletonModelTest {
	private static DateFormat dateFormat;
	private static GMapsPosition position;
	
	@BeforeClass
	public static void globalSetUp() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		position = Mockito.mock(GMapsPosition.class);
	}

	@Before
	public void setUp() {
		SingletonModel.INSTANCE.loadMap(new TreeMap<String, List<PointOfInterest>>());
	}
	
	@Test
	public void firstInsertionTest() throws ParseException {
		Date date = dateFormat.parse("2016-06-03-10.00.00");
		PointOfInterest museumA = new PointOfInterest("Museum A", date, position, "1");
		SingletonModel.INSTANCE.insertNewPointOfInterest(museumA);
		assertEquals(1, SingletonModel.INSTANCE.size());
	}
}