package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

public class PRAllTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	@After
	public void flushModel() {

		String groupID = "-3";
		try {
			CurrentModel.getCurrentModel().clear(groupID);
		} catch (NoSuchElementException e) {
			System.err.println(e.getMessage());

		}
	}

	final Message myMessage = Mockito.mock(Message.class);
	final Chat myChat = Mockito.mock(Chat.class);

	@Test
	public void testPRAll() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/programma",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43", "/programma");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPR = new PRAll();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Non e' stato impostato alcun punto di ritrovo", outputString);

		final IFunctionality myPRSet = new PRSet();
		final String outputString2 = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/08/2016 15:43",
				outputString2);

		final String outputString3 = myPR.exec(myMessage);
		final String expectedOutput = "Il programma del viaggio e' il seguente:\n"
				+ "- [Via terrazzano, 14 Rho\n07/08/2016 15:43](https://www.google.it/maps/place/Via+Terrazzano,+14,+20017+Rho+MI,+Italy+)\n";
		assertEquals(expectedOutput, outputString3);

	}

	@Test
	public void testPRAllOrder() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/10/2016 15:43",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 02/08/2016 15:43",
				"/set_punto_ritrovo Via terrazzano, 14 Rho 01/09/2016 15:43", "/programma");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/10/2016 15:43",
				outputString);
		final String outputString2 = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n02/08/2016 15:43",
				outputString2);
		final String outputString3 = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n01/09/2016 15:43",
				outputString3);

		final IFunctionality myPR = new PRAll();
		final String outputString4 = myPR.exec(myMessage);
		final String expectedOutput = "Il programma del viaggio e' il seguente:\n"
				+ "- [Via terrazzano, 14 Rho\n02/08/2016 15:43](https://www.google.it/maps/place/Via+Terrazzano,+14,+20017+Rho+MI,+Italy+)\n"
				+ "- [Via terrazzano, 14 Rho\n01/09/2016 15:43](https://www.google.it/maps/place/Via+Terrazzano,+14,+20017+Rho+MI,+Italy+)\n"
				+ "- [Via terrazzano, 14 Rho\n07/10/2016 15:43](https://www.google.it/maps/place/Via+Terrazzano,+14,+20017+Rho+MI,+Italy+)\n";
		assertEquals(expectedOutput, outputString4);

	}

	@Test
	public void testPRAllInputError() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/comando_errato");

		final IFunctionality myPR = new PRAll();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Formato input non corretto", outputString);

	}

}
