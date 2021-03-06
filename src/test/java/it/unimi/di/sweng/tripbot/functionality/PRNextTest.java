package it.unimi.di.sweng.tripbot.functionality;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.functionality.IFunctionality;
import it.unimi.di.sweng.tripbot.functionality.PRNext;
import it.unimi.di.sweng.tripbot.functionality.PRSet;
import it.unimi.di.sweng.tripbot.model.CurrentModel;

public class PRNextTest {

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
	public void testPRNext() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/prossimoritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);

		final IFunctionality myPR = new PRNext();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Non e' stato impostato alcun punto di ritrovo", outputString);

	}

	@Test
	public void testPRGetNext() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43",
				"/prossimoritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/08/2016 15:43",
				outputString);

		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals(
				"Prossimo ritrovo:\n" +
				"[Via terrazzano, 14 Rho\n" +
				"07/08/2016 15:43](https://www.google.it/maps/place/Via+Terrazzano,+14,+20017+Rho+MI,+Italy+)",
				outputString2);

	}

	@Test
	public void testPRNextNotExist() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2011 15:43",
				"/prossimoritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/08/2011 15:43",
				outputString);

		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals("Non sono previsti altri punti di ritrovo", outputString2);

	}

	@Test
	public void testPRNextErrorCommand() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/comando_inesistente");

		final IFunctionality myPRNext = new PRNext();
		final String outputString2 = myPRNext.exec(myMessage);
		assertEquals("Formato input non corretto", outputString2);

	}

	@Test
	public void testPRGetNextWhenMore() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43",
				"/setpuntoritrovo Via terrazzano, 14 Rho 06/08/2016 15:43",
				"/setpuntoritrovo Via terrazzano, 14 Rho 09/08/2016 15:43", "/prossimoritrovo");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		final String outputString = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/08/2016 15:43",
				outputString);

		final String outputString2 = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n06/08/2016 15:43",
				outputString2);

		final String outputString3 = myPRSet.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n09/08/2016 15:43",
				outputString3);

		final IFunctionality myPRNext = new PRNext();
		final String outputString4 = myPRNext.exec(myMessage);
		assertEquals(
				"Prossimo ritrovo:\n" +
				"[Via terrazzano, 14 Rho\n" +
				"06/08/2016 15:43](https://www.google.it/maps/place/Via+Terrazzano,+14,+20017+Rho+MI,+Italy+)", outputString4);

	}

}
