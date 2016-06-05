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
		final CommandParser PARSER = new CommandParser ("set_punto_ritrovo");

		assertEquals(PRManager.class, PARSER.dispatcher().getClass());
	}
}
