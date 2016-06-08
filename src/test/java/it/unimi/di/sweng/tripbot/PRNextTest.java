package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

public class PRNextTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	final Message myMessage = Mockito.mock(Message.class);
	final Chat myChat = Mockito.mock(Chat.class);
	
	@Test
	public void testPRNext() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/prossimo_ritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-3);
		
		final IFunctionality myPR = new PRNext();
		final String outputString = myPR.exec(myMessage);
		assertEquals("non e' stato impostato alcun punto di ritrovo", outputString);
		
	}
	
	@Test
	public void testPRGetNext() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43", "/prossimo_ritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-4);
		
		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2016 15:43' impostato", outputString);
		
		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals("il prossimo punto di ritrovo e': Via terrazzano, 14 Rho 07/08/2016 15:43", outputString2);
		
	}
		
}
