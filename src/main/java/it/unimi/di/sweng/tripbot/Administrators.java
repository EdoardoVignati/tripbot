package it.unimi.di.sweng.tripbot;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.ChatMember;
import com.pengrad.telegrambot.request.GetChatAdministrators;

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
