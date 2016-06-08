package it.unimi.di.sweng.tripbot.Geolocalization;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import it.unimi.di.sweng.tripbot.Configs;
@Ignore
public class GeolocalizationTest {

	@Test
	public void testConfiguration() 
	{		
		int port = Configs.INSTANCE.PORT;
		String googleToken = Configs.INSTANCE.GOOGLE_TOKEN;
		String telegramServerToken = Configs.INSTANCE.SERVER_TOKEN;
		String telegramBotToken = Configs.INSTANCE.BOT_TOKEN;		
		
		System.err.println(port);
		System.err.println(googleToken);
		System.err.println(telegramServerToken);
		System.err.println(telegramBotToken);
		
	}
	
	@Test
	public void testGmapsPosition()
	{
		APosition pos = new GmapsPosition(15.012345, -15.012345, "Nice Position, 012345, Nice Town");
		assertEquals("toString()", "Location Name: Nice Position, 012345, Nice Town;	Latitude: 15.012345;	Longitude: -15.012345", pos.toString());
	}
	
	@Test
	public void testGetPositionByName() throws Exception
	{
		ILocationService loc = new LocationProvider();
		APosition pos = loc.getPositionByName("Via Comelico, 14, Milano");
		assertEquals("getPositionByName()", "Location Name: Via Comelico, 14, 20135 Milano, Italy;	Latitude: 45.455030;	Longitude: 9.212389", pos.toString());
	}
	
	@Test(expected = Exception.class)
	public void testGetPositionByNameNoResult() throws Exception
	{
		ILocationService loc = new LocationProvider();
		APosition pos = loc.getPositionByName("ksfhskefhksjhksjfhksj");		
	}
	
	@Test
	public void testGetPositionByCoordinates() throws Exception
	{
		ILocationService loc = new LocationProvider();
		APosition pos = loc.getPositionByCoordinates(45.455030, 9.212389);
		assertEquals("getPositionByCoordinates()", "Location Name: Via Comelico, 14, 20135 Milano, Italy;	Latitude: 45.455030;	Longitude: 9.212389", pos.toString());
	}
	
	@Test(expected = Exception.class)
	public void testGetPositionByCoordinatesNoResult() throws Exception
	{
		ILocationService loc = new LocationProvider();
		APosition pos = loc.getPositionByCoordinates(789,432);		
	}
	
	@Test
	public void testGetPositionConcurrency() throws Exception
	{
		ILocationService loc = new LocationProvider();
		APosition pos = loc.getPositionByName("Via Comelico, 14, Milano");
		APosition pos3 = loc.getPositionByName("Via Puccini");
		APosition pos1 = loc.getPositionByName("Via Brosete, Bergamo");
		APosition pos2 = loc.getPositionByName("Via Suardi, Bergamo");		
		
		assertEquals("getPositionByName()", "Location Name: Via Comelico, 14, 20135 Milano, Italy;	Latitude: 45.455030;	Longitude: 9.212389", pos.toString());		
	}
	
	@Test
	public void testGetMapLink() throws Exception
	{
		ILocationService loc = new LocationProvider();
		APosition pos = loc.getPositionByName("Via Comelico, 14, Milano");
		assertEquals("getMapLink()", "https://www.google.it/maps/place/Via+Comelico,+14,+20135+Milano,+Italy+", loc.getMapLink(pos).toString());
	}

}
