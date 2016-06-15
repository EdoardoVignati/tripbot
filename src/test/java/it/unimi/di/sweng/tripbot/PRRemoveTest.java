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

public class PRRemoveTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	final Message myMessage = Mockito.mock(Message.class);
	final Chat myChat = Mockito.mock(Chat.class);

	@After
	public void flushModel() {

		String groupID = "-2";
		try {
			CurrentModel.getCurrentModel().clear(groupID);
		} catch (NoSuchElementException e) {
			System.err.println(e.getMessage());

		}
	}

	@Test
	public void testPRremoveGetProgramIndex() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43",
				"/setpuntoritrovo Rho 09/09/2017 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		myPRSet.exec(myMessage);
		myPRSet.exec(myMessage);
		
		final IFunctionality myPRRemove = new PRRemove();

		Mockito.when(myMessage.text()).thenReturn("/remove");
		final String outputString3 = myPRRemove.exec(myMessage);

		assertEquals(
				"Il programma del viaggio e' il seguente:\n1 - Via terrazzano, 14 Rho 07/08/2016 15:43\n2 - Rho 09/09/2017 15:43\n",
				outputString3);

	}
	
	@Test
	public void testPRremoveSinglePoi() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		myPRSet.exec(myMessage);

		final IFunctionality myPRRemove = new PRRemove();

		Mockito.when(myMessage.text()).thenReturn("/remove 1");
		final String outputString3 = myPRRemove.exec(myMessage);

		assertEquals("Punto di ritrovo:\n - 1 cancellato",outputString3);
		
	}
	
	@Test
	public void testPRremoveMultiPoi() throws Exception {

		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43",
				"/setpuntoritrovo Via terrazzano, 14 Rho 12/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		myPRSet.exec(myMessage);
		myPRSet.exec(myMessage);

		final IFunctionality myPRRemove = new PRRemove();

		Mockito.when(myMessage.text()).thenReturn("/remove 1 2 3");
		final String outputString3 = myPRRemove.exec(myMessage);

		assertEquals("Punto di ritrovo:\n - 1 cancellato\n - 2 cancellato\n - 3 non cancellato",outputString3);
		
	}
	
	@Test
	public void testPRremoveNothing() throws Exception {

		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRRemove = new PRRemove();
		
		Mockito.when(myMessage.text()).thenReturn("/remove 2 322 2");
		final String outputString3 = myPRRemove.exec(myMessage);

		assertEquals("Non e' stato impostato alcun punto di ritrovo",outputString3);
		
	}
	
	@Test
	public void testPRremoveErrors() throws Exception {
		
		Mockito.when(myMessage.text()).thenReturn("/setpuntoritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		myPRSet.exec(myMessage);
		
		Mockito.when(myMessage.text()).thenReturn("/qualcosa", "/remove ciao 5 4");
		
		final IFunctionality myPRRemove = new PRRemove();
		
		final String outputString = myPRRemove.exec(myMessage);
		assertEquals("Formato input non corretto",outputString);
		
		final String outputString2 = myPRRemove.exec(myMessage);
		assertEquals("Formato input non corretto",outputString2);
		
	}

}
