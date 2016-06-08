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
		
		final int INDICE_GRUPPO_LUOGO = 2;
		final int INDICE_GRUPPO_DATA = 3;
		final int INDICE_GRUPPO_ORA = 4;

		final String testoMessaggio = message.text();
		
	    final Pattern myPattern = Pattern.compile(pattern);

	    final Matcher myRegExpMatcher = myPattern.matcher(testoMessaggio);
	    
	    if ( !myRegExpMatcher.find() )
	    	return "formato input non corretto";
		
		final String luogo = myRegExpMatcher.group(INDICE_GRUPPO_LUOGO);
		final String data = myRegExpMatcher.group(INDICE_GRUPPO_DATA);
		final String ora = myRegExpMatcher.group(INDICE_GRUPPO_ORA);
		
		final String groupID = message.chat().id().toString();
		
		try {
			
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date dataPR = formatterData.parse(data + " " + ora);

			final ILocationService myLocationProvider = new LocationProvider();
			final APosition gmapsPosition = myLocationProvider.getPositionByName(luogo);
			
			final PointOfInterest newPR = new PointOfInterest(luogo, dataPR, gmapsPosition, groupID);
			
			final IModel myModel = SingletonModel.INSTANCE;
			myModel.insertNewPointOfInterest(newPR);
			
			return "punto di ritrovo '" + luogo + " " + formatterData.format(dataPR) + "' impostato";
			
		} catch (Exception e) {
			
			return "luogo non trovato su google maps";
			
		}
		
	}

}
