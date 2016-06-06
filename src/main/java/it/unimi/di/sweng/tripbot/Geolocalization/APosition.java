package it.unimi.di.sweng.tripbot.Geolocalization;

public abstract class APosition 
{
	final double latitude;
	final double longitude;
	final String streetName;
	
	public APosition(double lat, double lon, String name)
	{
		latitude = lat;
		longitude = lon;
		streetName = name;
	}
}
