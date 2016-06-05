package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class CommandParserTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	@Test
	public void testSetPuntoRitrovo() {
		final CommandParser PARSER = new CommandParser("set_punto_ritrovo");

		assertEquals(PRManager.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testProssimoRitrovo() {
		final CommandParser PARSER = new CommandParser("prossimo_ritrovo");

		assertEquals(PRManager.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testProgramma() {
		final CommandParser PARSER = new CommandParser("programma");

		assertEquals(PRManager.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testSOS() {
		final CommandParser PARSER = new CommandParser("sos");

		assertEquals(SOSManager.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testHelpAndDefault() {
		CommandParser PARSER = new CommandParser("help");

		assertEquals(HelpManager.class, PARSER.dispatcher().getClass());

		PARSER = new CommandParser("qualsiasi_altro_input");
		assertEquals(HelpManager.class, PARSER.dispatcher().getClass());
	}

	@Test
	public void testStart() {
		CommandParser PARSER = new CommandParser("start");

		assertEquals(StartManager.class, PARSER.dispatcher().getClass());
	}
}
