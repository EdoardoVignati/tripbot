package it.unimi.di.sweng.tripbot.model;

import java.util.List;

import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;

public interface IModel {
	public void insertNewPointOfInterest(final PointOfInterest pointOfInterest);

	public PointOfInterest getPointOfInterest(final String groupId, final String name);

	public List<PointOfInterest> getPointOfInterestList(final String groupId);

	public void removePointOfInterest(final PointOfInterest toRemove);

	public void clear(String groupID);
}
