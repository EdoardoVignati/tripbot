package it.unimi.di.sweng.tripbot.functionality;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.functionality.IFunctionality;
import it.unimi.di.sweng.tripbot.functionality.StartManager;

public class StartManagerTest {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	final Message myMessage = Mockito.mock(Message.class);
	
	@Test
	public void testStartManager() throws Exception {
		
		final IFunctionality myStartManager = new StartManager();
		final String outputString = myStartManager.exec(myMessage);
		assertEquals("Ciao, sono TripBot, un bot che permette di organizzare e gestire viaggi di gruppo", outputString);
	}
		

}
