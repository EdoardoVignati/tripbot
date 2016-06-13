package it.unimi.di.sweng.tripbot.Geolocalization;

public class GmapsPosition extends APosition 
{
	protected GmapsPosition(double lat, double lon, String name) 
	{		
		super(lat, lon, name,generateLink(name));		
	}
	
	public String toString()
	{		
		return String.format("Location Name: %1$s;\tLatitude: %2$f;\tLongitude: %3$f\tUrl: %4$s", streetName, latitude, longitude,mapUrl);		
	}

	private static String generateLink(String streetName) 
	{
		final String linkHeader = "https://www.google.it/maps/place/";
		final String name = streetName;
		String linkBody = "";
		
		final String[] nameWords = name.split("\\s");
		for(String s : nameWords)
		{
			linkBody += s + "+";
		}
		String link = linkHeader + linkBody;		
		return link;
	}
		
}
