package it.unimi.di.sweng.echobot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;

/**
 * Comunica a Telegram l'URL del <em>webhook</em> relativo alle <em>update</em>.
 */
public class SetWebHook {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Please provide the webhook URL as argument!");
			System.exit(-1);
		}
		final String url = args[0];
		TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);
		BaseResponse response = bot.execute(new SetWebhook().url(url));
		System.out.println("Telegram response was:" + response);
	}

}
