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

public class PRAllTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	@Before
	public void flushModel() {
		SingletonModel.INSTANCE.loadMap(new TreeMap<String, List<PointOfInterest>> ());
	}
	
	final Message myMessage = Mockito.mock(Message.class);
	final Chat myChat = Mockito.mock(Chat.class);
	
	@Test
	public void testPRAll() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/programma", "/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43", "/programma");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-3);
		
		final IFunctionality myPR = new PRAll();
		final String outputString = myPR.exec(myMessage);
		assertEquals("non e' stato impostato alcun punto di ritrovo", outputString);
		
		final IFunctionality myPRSet = new PRSet();
		final String outputString2 = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/08/2016 15:43' impostato", outputString2);
		
		final String outputString3 = myPR.exec(myMessage);
		final String expectedOutput =
				"Il programma del viaggio e' il seguente:\n" +
				"- Via terrazzano, 14 Rho 07/08/2016 15:43\n";
		assertEquals(expectedOutput, outputString3);
		
	}
	
	@Test
	public void testPRAllOrder() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn(
				"/set_punto_ritrovo Via terrazzano, 14 Rho 07/10/2016 15:43",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 02/08/2016 15:43",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 01/09/2016 15:43",
				"/programma");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long)-3);
		
		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 07/10/2016 15:43' impostato", outputString);
		final String outputString2 = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 02/08/2016 15:43' impostato", outputString2);
		final String outputString3 = myPRSet.exec(myMessage);
		assertEquals("punto di ritrovo 'Via terrazzano, 14 Rho 01/09/2016 15:43' impostato", outputString3);
		
		final IFunctionality myPR = new PRAll();
		final String outputString4 = myPR.exec(myMessage);
		final String expectedOutput =
				"Il programma del viaggio e' il seguente:\n"+
				"- Via terrazzano, 14 Rho 02/08/2016 15:43\n"+
				"- Via terrazzano, 14 Rho 01/09/2016 15:43\n"+
				"- Via terrazzano, 14 Rho 07/10/2016 15:43\n";
		assertEquals(expectedOutput, outputString4);
		
	}
		
}
