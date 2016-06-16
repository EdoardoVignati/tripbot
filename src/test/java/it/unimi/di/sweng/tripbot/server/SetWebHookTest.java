package it.unimi.di.sweng.tripbot.server;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.server.SetWebHook;

public class SetWebHookTest {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));

	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);

	}

	@Test
	public void testMain() throws Exception {
		String[] args = new String[1];
		args[0] = "hcudf";
		SetWebHook.main(args);
		assertTrue(outContent.toString().contains("Telegram response was:"));
	}

	@Test
	public void testMainNoArgs() throws Exception {
		String[] args = new String[0];
		exit.expectSystemExitWithStatus(-1);
		SetWebHook.main(args);
	}

}
