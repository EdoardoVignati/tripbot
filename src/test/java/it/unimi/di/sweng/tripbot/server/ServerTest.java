package it.unimi.di.sweng.tripbot.server;

import java.net.BindException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.server.Server;

public class ServerTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	private void test1() throws Exception {
		Server server = new Server();
		server.start();
		server.stop();
	};

	private void test2() throws Exception {
		Server.main(null);
	};

	@Test
	public void testOrder1() throws Exception {
		test1();
		test2();
	}

	@Test(expected = BindException.class)
	public void testOrder2() throws Exception {
		test2();
		test1();
	}

}
