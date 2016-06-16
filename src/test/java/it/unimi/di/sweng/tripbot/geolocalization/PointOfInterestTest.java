package it.unimi.di.sweng.tripbot.geolocalization;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import it.unimi.di.sweng.tripbot.geolocalization.GmapsPosition;
import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;

public class PointOfInterestTest {
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

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
