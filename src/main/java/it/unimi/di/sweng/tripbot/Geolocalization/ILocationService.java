package it.unimi.di.sweng.tripbot.Geolocalization;

public interface ILocationService 
{
	public APosition getPositionByName(String streetName) throws Exception;
	public APosition getPositionByCoordinates(double lat, double lon) throws Exception;
	public AMapLink getMapLink(APosition position);
}
