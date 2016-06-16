package it.unimi.di.sweng.tripbot.geolocalization;

public abstract class APosition
{
	public final double latitude;
	public final double longitude;
	public final String streetName;
	public final String mapUrl;
	
	public APosition(double lat, double lon, String name, String url)
	{
		latitude = lat;
		longitude = lon;
		streetName = name;
		mapUrl = url;
	}
		
}
