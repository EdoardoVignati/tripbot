package it.unimi.di.sweng.tripbot.server;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.restlet.routing.Router;

import it.unimi.di.sweng.tripbot.server.BotApplication;

public class BotApplicationTest {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max
	
	@Test
	public void testCreateInboundRootSize() {
		Router router = (Router) new BotApplication().createInboundRoot();

		assertEquals("\"/bot/{token}\"", router.getRoutes().get(0).toString().split("->")[0].trim());
	}
}
