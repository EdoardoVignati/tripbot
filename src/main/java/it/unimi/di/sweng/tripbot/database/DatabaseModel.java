package it.unimi.di.sweng.tripbot.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import it.unimi.di.sweng.tripbot.geolocalization.APosition;
import it.unimi.di.sweng.tripbot.geolocalization.LocationProvider;
import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;
import it.unimi.di.sweng.tripbot.model.IModel;
import it.unimi.di.sweng.tripbot.server.Configs;

public class DatabaseModel implements IModel {
	public Database db;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DatabaseModel() {
		try {
			db = new Database(Configs.INSTANCE.DB);
		} catch (SQLException e) {
			System.err.println("Errore connessione database");
		}
	}

	@Override
	public synchronized void insertNewPointOfInterest(PointOfInterest pointOfInterest) {
		String chat_id, poi, address;
		chat_id = pointOfInterest.groupId;
		poi = pointOfInterest.name;
		address = pointOfInterest.position.toString();
		Date meet_date = pointOfInterest.meetDate;
		String dateString = dateFormat.format(meet_date);
		try {
			db.execQuery("INSERT INTO trips(chat_id, poi, address, meet_date) VALUES('" + chat_id + "','" + poi + "','"
					+ address + "','" + dateString + "');");
		} catch (SQLException e) {
			System.err.println("Errore query database");
		}
	}

	@Override
	public synchronized PointOfInterest getPointOfInterest(String groupId, String name) {
		Date meetDate;
		APosition position;
		ResultSet rs;
		try {
			rs = db.execQuery(
					"SELECT address, meet_date FROM trips WHERE chat_id=" + groupId + " AND poi='" + name + "';");
			if (rs.next()) {
				meetDate = rs.getTimestamp("meet_date");
				position = (new LocationProvider().getPositionByName(rs.getString("address").split(":|\\;")[1]));
				rs.close();
				return new PointOfInterest(name, meetDate, position, groupId);
			}
		} catch (SQLException e) {
			System.err.println("Errore query database");
		} catch (Exception f) {
			System.err.println("Position error");
		}
		return null;
	}

	@Override
	public synchronized List<PointOfInterest> getPointOfInterestList(final String groupId) {
		List<PointOfInterest> pointList = new ArrayList<PointOfInterest>();
		String name;
		APosition position;
		Date meetDate;
		ResultSet rs;
		try {
			rs = db.execQuery("SELECT meet_date, poi, address FROM trips WHERE chat_id='" + groupId + "';");
			while (rs.next()) {
				name = rs.getString("poi");
				position = (new LocationProvider().getPositionByName(rs.getString("address").split(":|\\;")[1]));
				meetDate = rs.getTimestamp("meet_date");
				pointList.add(new PointOfInterest(name, meetDate, position, groupId));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println("Errore query database");
		}
		if (pointList.size() == 0)
			throw new NoSuchElementException();

		return pointList;
	}

	public void clear(String groupID) {
		try {
			db.execQuery("DELETE FROM trips WHERE chat_id=" + groupID);
		} catch (SQLException e) {
			System.err.println("Errore query database");
		}
	}

	@Override
	public synchronized void removePointOfInterest(PointOfInterest toRemove) {
		try {
			db.execQuery("DELETE FROM trips WHERE chat_id='" + toRemove.groupId + "' AND poi='" + toRemove.name
					+ "' AND meet_date='" + toRemove.meetDate + "'");
		} catch (SQLException e) {
			System.err.println("Errore query delete database");
		}
	}

}
