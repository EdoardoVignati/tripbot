package it.unimi.di.sweng.tripbot;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.model.SingletonModel;

public class PRNext implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "(\\/prossimo_ritrovo)";
		
		final String testoMessaggio = message.text();
		
	    final Pattern myPattern = Pattern.compile(pattern);

	    final Matcher myRegExpMatcher = myPattern.matcher(testoMessaggio);
	    
	    if ( !myRegExpMatcher.find() )
	    	return "formato input non corretto";
	    
	    final String groupID = message.chat().id().toString();
	    
	    try {
			
			final List<PointOfInterest> myPRList = SingletonModel.INSTANCE.getPointOfInterestList(groupID);
			
		} catch (NoSuchElementException e) {
			
			return "non e' stato impostato alcun punto di ritrovo";
			
		}
		
		return "il prossimo punto di ritrovo e': luogo data HH:MM";
	}

}
