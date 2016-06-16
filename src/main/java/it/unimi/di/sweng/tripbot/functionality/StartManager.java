package it.unimi.di.sweng.tripbot.functionality;

import com.pengrad.telegrambot.model.Message;

public class StartManager implements IFunctionality {

	@Override
	public String exec(Message message) {
		return "Ciao, sono TripBot, un bot che permette di organizzare e gestire viaggi di gruppo";
	}

}
