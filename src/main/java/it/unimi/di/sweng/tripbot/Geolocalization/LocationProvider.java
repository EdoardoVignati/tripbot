package it.unimi.di.sweng.tripbot.Geolocalization;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import it.unimi.di.sweng.tripbot.Configs;

public class LocationProvider implements ILocationService 
{
	//contesto di accesso alle API
	private GeoApiContext context = new GeoApiContext().setApiKey(Configs.INSTANCE.GOOGLE_TOKEN);
	private Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
	
	
	@Override
	public APosition getPositionByName(String streetName) throws Exception 
	{
		//geocoding
		GeocodingResult result;
		try 
		{
			result = GeocodingApi.geocode(context, streetName).await()[0];
		} catch (Exception e) 
		{
			logger.log(Level.INFO, getClass().getSimpleName() + " - No Result");
			//System.err.println("No Results Exception - getPositionByName");
			throw new Exception("No Results");
		}
			
		
		final String posName = result.formattedAddress;
		final double posLat = result.geometry.location.lat;
		final double posLon = result.geometry.location.lng;
				
		APosition position = new GmapsPosition(posLat, posLon, posName);
		return position;
	}

	@Override
	public APosition getPositionByCoordinates(double lat, double lon) throws Exception 
	{
		//reverse geocoding
		GeocodingResult result;
		try 
		{
			LatLng location = new LatLng(lat, lon);
			result = GeocodingApi.reverseGeocode(context, location).await()[0];
		} catch (Exception e) 
		{
			logger.log(Level.INFO, getClass().getSimpleName() + " - No Result");
			//System.err.println("No Results Exception - getPositionByCoordinates");
			throw new Exception("No Results");
		}
						
		final String posName = result.formattedAddress;
		final double posLat = result.geometry.location.lat;
		final double posLon = result.geometry.location.lng;
				
		APosition position = new GmapsPosition(posLat, posLon, posName);
		return position;
	}

}
