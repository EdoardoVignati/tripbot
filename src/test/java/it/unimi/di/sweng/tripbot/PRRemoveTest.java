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

		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		myPRSet.exec(myMessage);

		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Rho 09/09/2017 15:43");
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

		Mockito.when(myMessage.text()).thenReturn("/set_punto_ritrovo Via terrazzano, 14 Rho 07/08/2016 15:43");
		Mockito.when(myMessage.chat()).thenReturn(myChat);
		Mockito.when(myChat.id()).thenReturn((long) -2);
		Mockito.when(myChat.type()).thenReturn(Chat.Type.Private);

		final IFunctionality myPRSet = new PRSet();
		myPRSet.exec(myMessage);

		final IFunctionality myPRRemove = new PRRemove();

		Mockito.when(myMessage.text()).thenReturn("/remove 1");
		final String outputString3 = myPRRemove.exec(myMessage);

		assertEquals("Punto di ritrovo cancellato",outputString3);
		
	}


}
