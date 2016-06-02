package it.unimi.di.sweng.tripbot.Geolocalization;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import it.unimi.di.sweng.tripbot.Configs;

public class LocationProvider implements ILocationService 
{
	//contesto di accesso alle API
	private GeoApiContext context = new GeoApiContext().setApiKey(Configs.INSTANCE.GOOGLE_TOKEN);
	
	@Override
	public APosition getPositionByName(String streetName) throws Exception 
	{
		//geocoding
		GeocodingResult[] matches =  GeocodingApi.geocode(context,streetName).await();
		GeocodingResult result = matches[0];
		
		if(result == null)
			throw new Exception("No Results");
		
		final String posName = result.formattedAddress;
		final double posLat = result.geometry.location.lat;
		final double posLon = result.geometry.location.lng;
				
		APosition position = new GmapsPosition(posLat, posLon, posName);
		return position;
	}

	@Override
	public APosition getPositionByCoordinates(double lat, double lon) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AMapLink getMapLink(APosition position) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
