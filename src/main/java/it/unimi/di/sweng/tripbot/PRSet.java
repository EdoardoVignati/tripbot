package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.Geolocalization.APosition;
import it.unimi.di.sweng.tripbot.Geolocalization.ILocationService;
import it.unimi.di.sweng.tripbot.Geolocalization.LocationProvider;
import it.unimi.di.sweng.tripbot.model.SingletonModel;

public class PRSet implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "(\\/set_punto_ritrovo) (.+) (\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d) (\\d\\d:\\d\\d)";

		final String testoMessaggio = message.text();
		
	    Pattern myPattern = Pattern.compile(pattern);

	    Matcher myMatcher = myPattern.matcher(testoMessaggio);
	    
	    if ( !myMatcher.find() )
	    	return "formato input non corretto";
		
		final String luogo = myMatcher.group(2);
		final String data = myMatcher.group(3);
		final String ora = myMatcher.group(4);
		
		try {
			
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date dataPR = formatterData.parse(data + " " + ora);
			final String groupID = message.chat().id().toString();
			final ILocationService myLocationProvider = new LocationProvider();
			final APosition gmapsPosition = myLocationProvider.getPositionByName(luogo);
			
			final PointOfInterest newPR = new PointOfInterest(luogo, dataPR, gmapsPosition, groupID);
			
			IModel model = SingletonModel.INSTANCE;
			model.insertNewPointOfInterest(newPR);
			
			return "punto di ritrovo '" + luogo + " " + formatterData.format(dataPR) + "' impostato";
			
		} catch (Exception e) {
			return "luogo non trovato su google maps";
		}
		
	}

}
