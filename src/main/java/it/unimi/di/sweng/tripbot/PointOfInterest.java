package it.unimi.di.sweng.tripbot;

import java.util.Date;
import it.unimi.di.sweng.tripbot.Geolocalization.GmapsPosition;

<<<<<<< HEAD
public class PointOfInterest {
	public final String name;
	public final Date meetDate;
	public final String groupId;
	public final GMapsPosition position;
=======
public abstract class PointOfInterest {
	protected final String name;
	protected final Date meetDate;
	protected final String groupId;
	protected final GmapsPosition position;
>>>>>>> google_api
	
	public PointOfInterest(final String name, final Date meetDate, final GmapsPosition position, final String groupId) {
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
