package it.unimi.di.sweng.tripbot.functionality;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;
import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterestDateComparator;
import it.unimi.di.sweng.tripbot.model.CurrentModel;

public class PRAll implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "/programma";
		
		final String testoMessaggio = message.text().trim();
	    
	    if ( ! testoMessaggio.contains(pattern) )
	    	return "Formato input non corretto";
	    
	    final String groupID = message.chat().id().toString();
	    
	    try {
			
			final List<PointOfInterest> myPRList = CurrentModel.getCurrentModel().getPointOfInterestList(groupID);
			
			Collections.sort(myPRList, new PointOfInterestDateComparator());
			
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			String output = "Il programma del viaggio e' il seguente:\n";
			
			for(PointOfInterest tmp : myPRList)
				output += "- [" + tmp.name + "\n" + formatterData.format(tmp.meetDate) + "](" +tmp.position.mapUrl+")\n";
			
			return output;
			
			
		} catch (NoSuchElementException e) {
			
			return "Non e' stato impostato alcun punto di ritrovo";
			
		}
	    
	}

}
