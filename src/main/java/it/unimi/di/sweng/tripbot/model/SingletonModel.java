package it.unimi.di.sweng.tripbot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;

public enum SingletonModel implements IModel {
	INSTANCE;

	private final static Map<String, List<PointOfInterest>> POINTS_OF_INTEREST = new TreeMap<String, List<PointOfInterest>>();

	@Override
	public synchronized void insertNewPointOfInterest(PointOfInterest pointOfInterest) {
		List<PointOfInterest> list;
		if ((list = POINTS_OF_INTEREST.get(pointOfInterest.groupId)) == null)
			list = new ArrayList<PointOfInterest>();
		list.add(pointOfInterest);
		POINTS_OF_INTEREST.put(pointOfInterest.groupId, list);
	}

	@Override
	public synchronized PointOfInterest getPointOfInterest(String groupId, String name) {
		List<PointOfInterest> list = POINTS_OF_INTEREST.get(groupId);
		if (list == null)
			throw new NoSuchElementException("Unexistent group with id " + groupId);
		PointOfInterest pointOfInterest;
		for (int i = 0; i < list.size(); i++)
			if ((pointOfInterest = list.get(i)).name.equals(name))
				return pointOfInterest;
		return null;
	}

	@Override
	public synchronized List<PointOfInterest> getPointOfInterestList(String groupId) {
		List<PointOfInterest> list = POINTS_OF_INTEREST.get(groupId);
		if (list == null)
			throw new NoSuchElementException("Unexistent group with id " + groupId);
		return new ArrayList<PointOfInterest>(list);
	}

	@Override
	public synchronized void removePointOfInterest(PointOfInterest toRemove) {
		List<PointOfInterest> list = POINTS_OF_INTEREST.get(toRemove.groupId);
		if (list != null) {
			for (int i = 0; i < list.size(); i++)
				if (list.get(i).name.equals(toRemove.name))
					list.remove(i);
		} else
			throw new NoSuchElementException("Unexistent group with id " + toRemove.groupId);
	}

	public synchronized void loadMap(final Map<String, List<PointOfInterest>> newMap) {
		POINTS_OF_INTEREST.clear();
		POINTS_OF_INTEREST.putAll(newMap);
	}

	public void clear(String groupID) {
		POINTS_OF_INTEREST.remove(groupID);
	}

	public synchronized int getNumberOfGroups() {
		return POINTS_OF_INTEREST.size();
	}

}
