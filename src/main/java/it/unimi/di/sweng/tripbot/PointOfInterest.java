package it.unimi.di.sweng.tripbot;

import java.util.Date;

public class PointOfInterest {
	public final String name;
	public final Date meetDate;
	public final String groupId;
	public final GMapsPosition position;
	
	public PointOfInterest(final String name, final Date meetDate, final GMapsPosition position, final String groupId) {
		this.name = name;
		this.meetDate = meetDate;
		this.position = position;
		this.groupId = groupId;
	}
	
	@Override
	public String toString() {
		String pointOfInterest =
				"Location: " + name + "\n" +
				"Date: " + meetDate.toString() + "\n" +
				"Position: " + position.toString() + "\n" +
				"Group ID: " + groupId;
		return pointOfInterest;
	}
}
