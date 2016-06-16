package it.unimi.di.sweng.tripbot.geolocalization;

import java.util.Comparator;

public class PointOfInterestDateComparator implements Comparator<PointOfInterest> {

	@Override
	public int compare(final PointOfInterest obj1, final PointOfInterest obj2) {
		
		return obj1.meetDate.compareTo(obj2.meetDate);
		
	}

}
