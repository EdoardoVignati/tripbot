package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import it.unimi.di.sweng.tripbot.Geolocalization.GmapsPosition;

public class PointOfInterestTest {

	@BeforeClass
	public static void setUpTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Test
	public void pointOfInterestTest() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		Date date = dateFormat.parse("2016-06-03-09.00.00");
		GmapsPosition mockPosition = Mockito.mock(GmapsPosition.class);
		Mockito.when(mockPosition.toString()).thenReturn("fake-position");
		String expectedOutput = 
				"Location: Museum A\nDate: Fri Jun 03 09:00:00 UTC 2016\nPosition: fake-position\nGroup ID: 1";
		PointOfInterest pointOfInterest = new PointOfInterest("Museum A", date, mockPosition, "1");
		assertEquals(expectedOutput, pointOfInterest.toString());
	}
}
