package it.unimi.di.sweng.tripbot;

import java.util.Date;
import it.unimi.di.sweng.tripbot.Geolocalization.GmapsPosition;

public abstract class PointOfInterest {
	protected final String name;
	protected final Date meetDate;
	protected final String groupId;
	protected final GmapsPosition position;
	
	public PointOfInterest(final String name, final Date meetDate, final GmapsPosition position, final String groupId) {
		this.name = name;
		this.meetDate = meetDate;
		this.position = position;
		this.groupId = groupId;
	}
}
