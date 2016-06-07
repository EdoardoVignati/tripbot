package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Message;


public class PRSetTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	final Message myMessage = Mockito.mock(Message.class);
	
	@Test
	public void testPRSet() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		
		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2016 15:43' impostato", outputString);
		
	}

}
