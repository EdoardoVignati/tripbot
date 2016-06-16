package it.unimi.di.sweng.tripbot.server;

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
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import it.unimi.di.sweng.tripbot.parser.CommandParser;

public class BotResource extends ServerResource {

	@Post
	public Representation update(Representation data) throws IOException {

		final String token = getAttribute("token");
		if (!Configs.INSTANCE.SERVER_TOKEN.equals(token)) {
			setStatus(Status.CLIENT_ERROR_FORBIDDEN, "Wrong server token");
			return null;
		}

		final Update update = BotUtils.parseUpdate(data.getText());
		if (update.updateId() == null) {
			getLogger().warning("Can't parse update, text was: \"" + data.getText() + "\"");
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Can't parse the update");
			return null;
		}
		getLogger().info("<= " + update);

		final Message message;
		try {
			message = update.message();
		} catch (NullPointerException e) {
			getLogger().warning("Inserire un comando");
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
					"Inserisci un comando valido controlla /help per i comandi disponibili");
			return null;
		}

		if (message != null) {
			final Chat chat = message.chat();

			if (message.entities() == null)
				return null;

			final Integer entitylenght = message.entities()[0].length();
			final String comando = message.text().substring(1, entitylenght).split("@")[0];
			CommandParser cp = new CommandParser(comando);

			final String answer = cp.dispatcher().exec(message);
			final TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);
			final SendResponse response = bot.execute(new SendMessage(chat.id(), answer).parseMode(ParseMode.Markdown));
			getLogger().info("=> " + response);

		}

		return null;
	}

}
