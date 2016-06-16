package it.unimi.di.sweng.tripbot.functionality;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.ChatMember;
import com.pengrad.telegrambot.request.GetChatAdministrators;

import it.unimi.di.sweng.tripbot.server.Configs;

public class Administrators {
	public static boolean isAmministratore(final long chatID, final int userID) {
		final TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);
		final List<ChatMember> chatMembers = bot.execute( new GetChatAdministrators(chatID) ).administrators();
		for(int i=0; i<chatMembers.size(); i++)
			if (chatMembers.get(i).user().id() == userID)
				return true;
		return false;
	}
}
