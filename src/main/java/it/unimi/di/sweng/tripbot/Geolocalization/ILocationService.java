package it.unimi.di.sweng.tripbot.Geolocalization;

public interface ILocationService 
{
	public APosition getPositionByName(String streetName);
	public APosition getPositionByCoordinates(double lat, double lon);
	public AMapLink getMapLink(APosition position);
}
