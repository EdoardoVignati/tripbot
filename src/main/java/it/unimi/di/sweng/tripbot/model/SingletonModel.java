package it.unimi.di.sweng.tripbot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.unimi.di.sweng.tripbot.IModel;
import it.unimi.di.sweng.tripbot.PointOfInterest;

public enum SingletonModel implements IModel {
	INSTANCE;

	private final static Map<String, List<PointOfInterest>> POINTS_OF_INTEREST =
			new TreeMap<String, List<PointOfInterest>>();
	
	@Override
	public synchronized void insertNewPointOfInterest(PointOfInterest pointOfInterest) {
		List<PointOfInterest> list;
		if ((list = POINTS_OF_INTEREST.get(pointOfInterest.groupId)) == null) 
			list = new ArrayList<PointOfInterest>();
		list.add(pointOfInterest);
		POINTS_OF_INTEREST.put(pointOfInterest.groupId, list);
	}

	@Override
	public PointOfInterest getPointOfInterest(String groupId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PointOfInterest> getPointOfInterestList(String groupId) {
		return new ArrayList<PointOfInterest>(POINTS_OF_INTEREST.get(groupId));
	}

	@Override
	public void removePointOfInterest(String groupId, String name) {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized void loadMap(final Map<String, List<PointOfInterest>> newMap) {
		POINTS_OF_INTEREST.clear();
		POINTS_OF_INTEREST.putAll(newMap);
	}
	
	public synchronized int getNumberOfGroups() {
		return POINTS_OF_INTEREST.size();
	}

}
