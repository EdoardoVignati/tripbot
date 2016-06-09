package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.model.SingletonModel;

public class PRAll implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "(\\/programma)";
		
		final String testoMessaggio = message.text();
		
	    final Pattern myPattern = Pattern.compile(pattern);

	    final Matcher myRegExpMatcher = myPattern.matcher(testoMessaggio);
	    
	    if ( !myRegExpMatcher.find() )
	    	return "formato input non corretto";
	    
	    final String groupID = message.chat().id().toString();
	    
	    try {
			
			final List<PointOfInterest> myPRList = SingletonModel.INSTANCE.getPointOfInterestList(groupID);
			
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
