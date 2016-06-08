package it.unimi.di.sweng.tripbot.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.unimi.di.sweng.tripbot.IModel;
import it.unimi.di.sweng.tripbot.PointOfInterest;
import it.unimi.di.sweng.tripbot.Geolocalization.APosition;
import it.unimi.di.sweng.tripbot.Geolocalization.LocationProvider;

public class Model implements IModel{
	private Database db;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
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
		String dateString = dateFormat.format(meet_date);
		try {
			db.execQuery("INSERT INTO trips(chat_id, poi, address, meet_date) VALUES('"+chat_id+"','"+poi+"','"+address+"','"+dateString+"');");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public PointOfInterest getPointOfInterest(String groupId, String name) {
		Date meetDate;
		APosition position;
		ResultSet rs;
		try {
			rs = db.execQuery("SELECT address, meet_date FROM trips WHERE chat_id='"+groupId+"' AND poi='"+name+"';");
			if(rs.next()){
				meetDate = rs.getTimestamp("meet_date");
				position = (new LocationProvider().getPositionByName(rs.getString("address").split(":|\\;")[1]));
				rs.close();
				return new PointOfInterest(name, meetDate, position, groupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
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
