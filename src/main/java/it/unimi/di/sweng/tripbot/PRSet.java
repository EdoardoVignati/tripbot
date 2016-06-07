package it.unimi.di.sweng.tripbot;

import com.pengrad.telegrambot.model.Message;

public class PRSet implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		String output = "";

		String testoMessaggio = message.text();
		
		String[] datiMessaggio = testoMessaggio.split(" ");
		
		for(int i=1; i<datiMessaggio.length; i++)
			output += " " + datiMessaggio[i];
		
		return "punto di ritrovo '" + output.trim() + "' impostato";
		
	}

}
