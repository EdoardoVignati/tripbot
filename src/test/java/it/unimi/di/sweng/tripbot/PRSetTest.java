package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;


public class PRSetTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	final Message myMessage = Mockito.mock(Message.class);
	final Chat myChat = Mockito.mock(Chat.class);
	
	@Test
	public void testPRSet() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-1);
		
		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2016 15:43' impostato", outputString);
		
	}
	
	@Test
	public void testPRSetErrors() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-1);
		
		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("formato input non corretto", outputString);

		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Rho 15:43");
		final String outputString2 = myPR.exec(myMessage);
		assertEquals("formato input non corretto", outputString2);
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Rho 07/08/2016");
		final String outputString3 = myPR.exec(myMessage);
		assertEquals("formato input non corretto", outputString3);
		
		Mockito.when(myMessage.text()).thenReturn("/altro_comando Rho 07/08/2016 15:30");
		final String outputString4 = myPR.exec(myMessage);
		assertEquals("formato input non corretto", outputString4);
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via pincopallo Ahrhrha, 2 15/08/2016 15:30");
		final String outputString5 = myPR.exec(myMessage);
		assertEquals("luogo non trovato su google maps", outputString5);
		
	}

}