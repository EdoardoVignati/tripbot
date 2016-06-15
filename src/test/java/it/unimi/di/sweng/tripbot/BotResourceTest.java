package it.unimi.di.sweng.tripbot;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;

public class BotResourceTest {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	@Test
	public void testNoCommand() throws IOException {
		ServerResource br = Mockito.spy(new BotResource());
		final StringRepresentation stringRepresentation = new StringRepresentation(
				"{\"update_id\":188379476,\n\"message\":{\"message_id\":70,\"from\":{\"id\":123456789,\"first_name\":\"Mario\",\"last_name\":\"Rossi\",\"username\":\"MaRo1234\"},\"chat\":{\"id\":123456789,\"first_name\":\"Mario\",\"last_name\":\"Rossi\",\"username\":\"MaRo1234\",\"type\":\"private\"},\"date\":1465246046,\"text\":\"jvfrio\"}}");

		Mockito.doReturn(Configs.INSTANCE.SERVER_TOKEN).when(br).getAttribute("token");
		assertEquals(((BotResource) br).update(stringRepresentation), null);
	}

	@Test
	public void testWrongToken() throws IOException {
		ServerResource br = Mockito.spy(new BotResource());
		final StringRepresentation stringRepresentation = new StringRepresentation("");

		Mockito.doReturn(Configs.INSTANCE.SERVER_TOKEN + "x").when(br).getAttribute("token");
		assertEquals(((BotResource) br).update(stringRepresentation), null);
	}

	@Test
	public void testNoUpdate() throws IOException {
		ServerResource br = Mockito.spy(new BotResource());
		final StringRepresentation stringRepresentation = new StringRepresentation("{\"update_id\":null}");

		Mockito.doReturn(Configs.INSTANCE.SERVER_TOKEN).when(br).getAttribute("token");
		assertEquals(((BotResource) br).update(stringRepresentation), null);
	}

	@Test
	public void testCommandValid() throws IOException {
		ServerResource br = Mockito.spy(new BotResource());
		final StringRepresentation stringRepresentation = new StringRepresentation(
				"{\"update_id\":188379476,\n\"message\":{\"message_id\":70,\"from\":{\"id\":123456789,\"first_name\":\"Mario\",\"last_name\":\"Rossi\",\"username\":\"MaRo1234\"},\"chat\":{\"id\":123456789,\"first_name\":\"Mario\",\"last_name\":\"Rossi\",\"username\":\"MaRo1234\",\"type\":\"private\"},\"date\":1465246046,\"text\":\"/setpuntoritrovo\",\"entities\":[{\"type\":\"bot_command\",\"offset\":0,\"length\":15}]}}");

		Mockito.doReturn(Configs.INSTANCE.SERVER_TOKEN).when(br).getAttribute("token");
		assertEquals(((BotResource) br).update(stringRepresentation), null);
	}
	
	@Test
	public void testCommandValid2() throws IOException {
		ServerResource br = Mockito.spy(new BotResource());
		final StringRepresentation stringRepresentation = new StringRepresentation(
				"{\"update_id\":188379476,\n\"message\":{\"message_id\":70,\"from\":{\"id\":123456789,\"first_name\":\"Mario\",\"last_name\":\"Rossi\",\"username\":\"MaRo1234\"},\"chat\":{\"id\":123456789,\"first_name\":\"Mario\",\"last_name\":\"Rossi\",\"username\":\"MaRo1234\",\"type\":\"private\"},\"date\":1465246046,\"text\":\"/setpuntoritrovo@qualcosa\",\"entities\":[{\"type\":\"bot_command\",\"offset\":0,\"length\":24}]}}");

		Mockito.doReturn(Configs.INSTANCE.SERVER_TOKEN).when(br).getAttribute("token");
		assertEquals(((BotResource) br).update(stringRepresentation), null);
	}
	
}