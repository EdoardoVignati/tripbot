package it.unimi.di.sweng.tripbot.geolocalization;

public interface ILocationService 
{
	public APosition getPositionByName(String streetName) throws Exception;
	public APosition getPositionByCoordinates(double lat, double lon) throws Exception;	
}
