package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.Geolocalization.APosition;
import it.unimi.di.sweng.tripbot.Geolocalization.ILocationService;
import it.unimi.di.sweng.tripbot.Geolocalization.LocationProvider;

public class PRSet implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "(\\/setpuntoritrovo)(@[^\\s]+){0,1} (.+) (\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d) (\\d\\d:\\d\\d)";
		
		final int INDICE_GRUPPO_LUOGO = 3;
		final int INDICE_GRUPPO_DATA = 4;
		final int INDICE_GRUPPO_ORA = 5;

		final String testoMessaggio = message.text();
		
	    final Pattern myPattern = Pattern.compile(pattern);

	    final Matcher myRegExpMatcher = myPattern.matcher(testoMessaggio);
	    
	    if ( !myRegExpMatcher.find() )
	    	return "Formato input non corretto";
		
		final String luogo = myRegExpMatcher.group(INDICE_GRUPPO_LUOGO);
		final String data = myRegExpMatcher.group(INDICE_GRUPPO_DATA);
		final String ora = myRegExpMatcher.group(INDICE_GRUPPO_ORA);
		
		final Chat myChat = message.chat();
		final Long chatID = myChat.id();
		
		if ( myChat.type() != Chat.Type.Private && !Administrators.isAmministratore(chatID, message.from().id()) )
			return "Solo gli amministratori possono impostare nuovi punti di ritrovo";
		
		try {
			
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date dataPR = formatterData.parse(data + " " + ora);

			final ILocationService myLocationProvider = new LocationProvider();
			final APosition gmapsPosition = myLocationProvider.getPositionByName(luogo);
			
			final PointOfInterest newPR = new PointOfInterest(luogo, dataPR, gmapsPosition, chatID.toString());
			
			final IModel myModel = CurrentModel.getCurrentModel();
			myModel.insertNewPointOfInterest(newPR);
			
			return "Impostato " + luogo + "\n" + gmapsPosition.toString().split(":|\\;")[1].trim() + "\n" + "" + formatterData.format(dataPR);
			
		} catch (Exception e) {
			
			return "Luogo non trovato su Google maps";
			
		}
		
	}
	
}
