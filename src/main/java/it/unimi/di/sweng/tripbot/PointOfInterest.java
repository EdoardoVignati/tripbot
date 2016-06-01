package it.unimi.di.sweng.tripbot;

import java.util.Date;

public abstract class PointOfInterest {
	protected final String name;
	protected final Date meetDate;
	protected final String groupId;
	protected final GMapsPosition position;
	
	public PointOfInterest(final String name, final Date meetDate, final GMapsPosition position, final String groupId) {
		this.name = name;
		this.meetDate = meetDate;
		this.position = position;
		this.groupId = groupId;
	}
}
