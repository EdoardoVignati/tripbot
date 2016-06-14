package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.model.Message;

public class PRAll implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "/programma";
		
		final String testoMessaggio = message.text().trim();
	    
	    if ( ! testoMessaggio.equals(pattern) )
	    	return "Formato input non corretto";
	    
	    final String groupID = message.chat().id().toString();
	    
	    try {
			
			final List<PointOfInterest> myPRList = CurrentModel.getCurrentModel().getPointOfInterestList(groupID);
			
			Collections.sort(myPRList, new PointOfInterestDateComparator());
			
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			String output = "Il programma del viaggio e' il seguente:\n";
			
			for(PointOfInterest tmp : myPRList)
				output += "- <a href='" + tmp.position.mapUrl + "'>" + tmp.name + " " + formatterData.format(tmp.meetDate) + "</a>\n";
			
			return output;
			
			
		} catch (NoSuchElementException e) {
			
			return "Non e' stato impostato alcun punto di ritrovo";
			
		}
	    
	}

}
