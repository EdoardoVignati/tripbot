package it.unimi.di.sweng.tripbot.Geolocalization;

public abstract class AMapLink 
{
	final String locationName;
	final String url;
	
	public AMapLink(String name, String url)
	{
		locationName = name;
		this.url = url;
	}
}
