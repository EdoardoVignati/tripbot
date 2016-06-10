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
	    	return "formato input non corretto";
	    
	    final String groupID = message.chat().id().toString();
	    
	    try {
			
			final List<PointOfInterest> myPRList = CurrentModel.getCurrentModel().getPointOfInterestList(groupID);
			
			final PointOfInterestComparator myComparator = new PointOfInterestDateComparator();
			
			Collections.sort(myPRList, myComparator);
			
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			String output = "Il programma del viaggio e' il seguente:\n";
			
			for(PointOfInterest tmp : myPRList)
				output += "- " + tmp.name + " " + formatterData.format(tmp.meetDate) + "\n";
			
			return output;
			
			
		} catch (NoSuchElementException e) {
			
			return "non e' stato impostato alcun punto di ritrovo";
			
		}
	    
	}

}
