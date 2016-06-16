package it.unimi.di.sweng.tripbot.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.functionality.HelpManager;
import it.unimi.di.sweng.tripbot.functionality.PRAll;
import it.unimi.di.sweng.tripbot.functionality.PRNext;
import it.unimi.di.sweng.tripbot.functionality.PRRemove;
import it.unimi.di.sweng.tripbot.functionality.PRSet;
import it.unimi.di.sweng.tripbot.functionality.StartManager;
import it.unimi.di.sweng.tripbot.parser.CommandParser;

public class CommandParserTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private CommandParser PARSER;

	@Test
	public void testSetPuntoRitrovo() {
		PARSER = new CommandParser("setpuntoritrovo");

		assertEquals(PRSet.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testProssimoRitrovo() {
		PARSER = new CommandParser("prossimoritrovo");

		assertEquals(PRNext.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testProgramma() {
		PARSER = new CommandParser("programma");

		assertEquals(PRAll.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testRemove() {
		PARSER = new CommandParser("remove");

		assertEquals(PRRemove.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testHelpAndDefault() {
		PARSER = new CommandParser("help");

		assertEquals(HelpManager.class, PARSER.dispatcher().getClass());

		PARSER = new CommandParser("qualsiasi_altro_input");
		assertEquals(HelpManager.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testStart() {
		PARSER = new CommandParser("start");

		assertEquals(StartManager.class, PARSER.dispatcher().getClass());
	}
}
