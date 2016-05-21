package it.unimi.di.sweng.echobot;

import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class BotResource extends ServerResource {
	
	@Post
	public Representation update(Representation data) throws IOException {

		// check that Telegram is using the server token
		
		final String token = getAttribute("token");
		if (!Configs.INSTANCE.SERVER_TOKEN.equals(token)) {
			setStatus(Status.CLIENT_ERROR_FORBIDDEN, "Worng server token");
			return null;
		}
		
		// get the update and try to parse it
		
		final Update update = BotUtils.parseUpdate(data.getText());
		if (update.updateId() == null) {
			getLogger().warning("Can't parse update, text was: \"" + data.getText() + "\"");
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Can't parse the update");
			return null;
		}
		getLogger().info("<= " + update);
		
		// get the message from the update and prepare the answer
		
		final Message message = update.message();
		final Chat chat = message.chat();
		final String answer = "Ciao " + chat.firstName() + ", il tuo ultimo messaggio è stato: \"" + message.text() + "\"";

		// sent the answer to Telegram
		
		final TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);
		final SendResponse response = bot.execute(new SendMessage(chat.id(), answer));
		getLogger().info("=> " + response);
		
		return null;
	}

}
