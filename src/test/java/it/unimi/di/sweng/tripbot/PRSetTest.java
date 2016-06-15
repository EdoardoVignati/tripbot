package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

public class PRSetTest {

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
	public void testPRSet() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/08/2016 15:43",
				outputString);

	}

	@Test
	public void testPRSetErrors() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Formato input non corretto", outputString);

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Rho 15:43");
		final String outputString2 = myPR.exec(myMessage);
		assertEquals("Formato input non corretto", outputString2);

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Rho 07/08/2016");
		final String outputString3 = myPR.exec(myMessage);
		assertEquals("Formato input non corretto", outputString3);

		Mockito.when(myMessage.text()).thenReturn("/altro_comando Rho 07/08/2016 15:30");
		final String outputString4 = myPR.exec(myMessage);
		assertEquals("Formato input non corretto", outputString4);

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via pincopallo Ahrhrha, 2 15/08/2016 15:30");
		final String outputString5 = myPR.exec(myMessage);
		assertEquals("Luogo non trovato su Google maps", outputString5);

	}
	
	@Test
	public void testPRSetWithAt() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo@qualcosa Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Impostato Via terrazzano, 14 Rho\nVia Terrazzano, 14, 20017 Rho MI, Italy\n07/08/2016 15:43", outputString);

	}
	
	@Test
	public void testPRSetWithAtError() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo@ Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -3);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPR = new PRSet();
		final String outputString = myPR.exec(myMessage);
		assertEquals("Formato input non corretto", outputString);

	}

}
