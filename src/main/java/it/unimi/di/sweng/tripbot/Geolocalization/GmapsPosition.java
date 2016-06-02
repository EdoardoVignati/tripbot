package it.unimi.di.sweng.tripbot.Geolocalization;

public class GmapsPosition extends APosition 
{
	public GmapsPosition(double lat, double lon, String name) 
	{
		super(lat, lon, name);		
	}
	
	public String toString()
	{		
		return String.format("Location Name: %1$s;\tLatitude: %2$f;\tLongitude: %3$f", streetName, latitude, longitude);		
	}
		
	}
