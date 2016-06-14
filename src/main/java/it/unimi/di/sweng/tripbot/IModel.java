package it.unimi.di.sweng.tripbot;

import java.util.List;

public interface IModel {
	public void insertNewPointOfInterest(final PointOfInterest pointOfInterest);
	public PointOfInterest getPointOfInterest(final String groupId, final String name);
	public List<PointOfInterest> getPointOfInterestList(final String groupId);
	public void removePointOfInterest(final String groupId, final String name);
}
