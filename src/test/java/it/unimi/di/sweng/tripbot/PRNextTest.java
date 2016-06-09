package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.model.SingletonModel;

public class PRNextTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	@Before
	public void flushModel() {
		SingletonModel.INSTANCE.loadMap(new TreeMap<String, List<PointOfInterest>> ());
	}
	
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
		Mockito.when(myChat.id()).thenReturn((long)-3);
		
		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2016 15:43' impostato", outputString);
		
		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals("il prossimo punto di ritrovo e': Via terrazzano, 14 Rho 07/08/2016 15:43", outputString2);
		
	}
	
	@Test
	public void testPRNextNotExist() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2011 15:43", "/prossimo_ritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-3);
		
		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2011 15:43' impostato", outputString);
		
		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals("non sono previsti altri punti di ritrovo", outputString2);
		
	}
	
	@Test
	public void testPRNextErrorCommand() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/comando_inesistente");
		
		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals("formato input non corretto", outputString2);
		
	}
	
	@Test
	public void testPRGetNextWhenMore() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn(
				"/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 06/08/2016 15:43",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 09/08/2016 15:43",
				"/prossimo_ritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-3);
		
		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2016 15:43' impostato", outputString);
		
		final String outputString2 = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 06/08/2016 15:43' impostato", outputString2);
		
		final String outputString3 = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 09/08/2016 15:43' impostato", outputString3);
		
		final IFunctionality myPRNext = new PRNext();
		final String outputString4 = myPRNext.exec(myMessage);
		assertEquals("il prossimo punto di ritrovo e': Via terrazzano, 14 Rho 06/08/2016 15:43", outputString4);
		
	}
		
}