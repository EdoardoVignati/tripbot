package it.unimi.di.sweng.tripbot;

public class PointOfInterestDateComparator implements PointOfInterestComparator {

	@Override
	public int compare(final PointOfInterest obj1, final PointOfInterest obj2) {
		
		return obj1.meetDate.compareTo(obj2.meetDate);
		
	}

}
