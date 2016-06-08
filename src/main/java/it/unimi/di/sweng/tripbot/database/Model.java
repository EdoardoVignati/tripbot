package it.unimi.di.sweng.tripbot.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.unimi.di.sweng.tripbot.IModel;
import it.unimi.di.sweng.tripbot.PointOfInterest;

public class Model implements IModel{
	Database db;
	public Model(){
		try {
			db = new Database(System.getenv("JDBC_DATABASE_URL"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertNewPointOfInterest(PointOfInterest pointOfInterest) {
		String chat_id, poi, address;
		chat_id = pointOfInterest.groupId;
		poi = pointOfInterest.name;
		address = pointOfInterest.position.toString();
		Date meet_date = pointOfInterest.meetDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateString = dateFormat.format(meet_date);
		try {
			db.execQuery("INSERT INTO trips(chat_id, poi, address, meet_date) VALUES('"+chat_id+"','"+poi+"','"+address+"','"+dateString+"');");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public PointOfInterest getPointOfInterest(String groupId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PointOfInterest> getPointOfInterestList(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePointOfInterest(String groupId, String name) {
		// TODO Auto-generated method stub
		
	}

}
