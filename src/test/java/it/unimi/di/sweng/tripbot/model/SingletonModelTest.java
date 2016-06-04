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
	private static Date futureDate;
	
	@BeforeClass
	public static void globalSetUp() throws ParseException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		position = Mockito.mock(GMapsPosition.class);
		futureDate = dateFormat.parse("2099-06-03-10.00.00");
	}

	@Before
	public void setUp() {
		SingletonModel.INSTANCE.loadMap(new TreeMap<String, List<PointOfInterest>>());
	}
	
	@Test
	public void firstInsertionTest() throws ParseException {
		PointOfInterest museumA = new PointOfInterest("Museum A", futureDate, position, "1");
		SingletonModel.INSTANCE.insertNewPointOfInterest(museumA);
		assertEquals(1, SingletonModel.INSTANCE.getNumberOfGroups());
	}
	
	@Test
	public void getPointOfInterestListTest() {
		PointOfInterest museumA = new PointOfInterest("Museum A", futureDate, position, "1");
		String pointOfInterestString = museumA.toString();
		SingletonModel.INSTANCE.insertNewPointOfInterest(museumA);
		List<PointOfInterest> group1List = SingletonModel.INSTANCE.getPointOfInterestList("1");
		assertEquals(1, group1List.size());
		assertEquals("[" + pointOfInterestString + "]", group1List.toString());
		group1List.add(Mockito.mock(PointOfInterest.class));
		assertEquals(1, SingletonModel.INSTANCE.getPointOfInterestList("1").size());
	}
	
	@Test
	public void multipleInsertionsTest() {
		PointOfInterest museumA = new PointOfInterest("Museum A", futureDate, position, "1");
		SingletonModel.INSTANCE.insertNewPointOfInterest(museumA);
		PointOfInterest museumB = new PointOfInterest("Museum B", futureDate, position, "1");
		SingletonModel.INSTANCE.insertNewPointOfInterest(museumB);
		assertEquals(1, SingletonModel.INSTANCE.getNumberOfGroups());
		assertEquals(2, SingletonModel.INSTANCE.getPointOfInterestList("1").size());
		PointOfInterest museumC = new PointOfInterest("Museum C", futureDate, position, "2");
		SingletonModel.INSTANCE.insertNewPointOfInterest(museumC);
		assertEquals(2, SingletonModel.INSTANCE.getNumberOfGroups());
	}
	
	@Test
	public void nullPointOfInterestListTest() {
		assertNull(SingletonModel.INSTANCE.getPointOfInterestList("3"));
	}
}