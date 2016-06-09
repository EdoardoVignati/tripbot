package it.unimi.di.sweng.tripbot;

import java.util.Comparator;

public interface PointOfInterestComparator extends Comparator<PointOfInterest> {
	
	public int compare(final PointOfInterest obj1, final PointOfInterest obj2);

}
