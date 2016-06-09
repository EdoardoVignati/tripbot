package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Message;

public class HelpManagerTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	final Message myMessage = Mockito.mock(Message.class);
	
	@Test
	public void testHelpManager() throws Exception {
		
		final IFunctionality myPRManager = new HelpManager();
		final String expected = "/set_punto_ritrovo luogo dd/mm/aaaa HH:MM : Imposta un nuovo punto di ritrovo\n" +
								"/prossimo_ritrovo : Visualizza le informazioni del prossimo punto di ritrovo impostato\n" +
								"/programma : Visualizza la lista di tutti i punti di ritrovo impostati\n";
		
		assertEquals(expected, myPRManager.exec(myMessage));
		
	}

}
