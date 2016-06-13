package it.unimi.di.sweng.tripbot.Geolocalization;

public abstract class APosition
{
	final double latitude;
	final double longitude;
	final String streetName;
	final String mapUrl;
	
	public APosition(double lat, double lon, String name, String url)
	{
		latitude = lat;
		longitude = lon;
		streetName = name;
		mapUrl = url;
	}
		
}
