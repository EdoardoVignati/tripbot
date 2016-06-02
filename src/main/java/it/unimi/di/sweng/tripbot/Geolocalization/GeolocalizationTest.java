package it.unimi.di.sweng.tripbot.Geolocalization;

import static org.junit.Assert.*;

import org.junit.Test;

import it.unimi.di.sweng.tripbot.Configs;

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
		GmapsPosition pos = new GmapsPosition(15.012345, -15.012345, "Nice Position, 012345, Nice Town");
		assertEquals("toString()", "Location Name: Nice Position, 012345, Nice Town;	Latitude: 15.012345;	Longitude: -15.012345", pos.toString());
	}

}
