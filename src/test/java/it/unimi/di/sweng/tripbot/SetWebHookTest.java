package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.Timeout;

public class SetWebHookTest {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);

	}

	@Test
	public void testMain() throws Exception {
		String[] args = new String[1];
		args[0] = "hcudf";
		SetWebHook.main(args);
		assertEquals("Telegram response was:BaseResponse{ok=true, error_code=0, description='Webhook is already set'}",
				outContent.toString().trim());
	}

	@Test
	public void testMainNoArgs() throws Exception {
		String[] args = new String[0];
		exit.expectSystemExitWithStatus(-1);
		SetWebHook.main(args);
	}

}
