package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.ChatMember;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetChatAdministrators;

public class PRRemove implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "/remove";

		final String testoMessaggio = message.text().trim();

		if (!testoMessaggio.contains(pattern))
			return "Formato input non corretto";

		IModel md = CurrentModel.getCurrentModel();

		final Chat myChat = message.chat();
		final Long chatID = myChat.id();
		final String groupID = chatID.toString();
		
		if ( myChat.type() != Chat.Type.Private && !isAmministratore(chatID, message.from().id()) )
			return "Solo gli amministratori possono eliminare i punti di ritrovo";

		int i = 1;

		final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		List<PointOfInterest> myPRList=null;
		try{
			myPRList = md.getPointOfInterestList(groupID);
		}catch(NoSuchElementException e){
			System.err.println("Database vuoto");
		}
		String[] indexEntries = testoMessaggio.split(" ");
		if (indexEntries.length == 1) {
			String output = "Il programma del viaggio e' il seguente:\n";
			for (PointOfInterest tmp : myPRList) {
				output += i + " - " + tmp.name + " " + formatterData.format(tmp.meetDate) + "\n";
				i++;
			}
			return output;
		} else {
			
			String feedback = "Punto di ritrovo:";

			for (int j = 1; j < indexEntries.length; j++) {
				
				int entry;
				
				try {
					entry = Integer.parseInt(indexEntries[j]);
				} catch (NumberFormatException ex) {
					return "Formato input non corretto";
				}
				
				if ( myPRList != null && entry <= myPRList.size() ) {
					md.removePointOfInterest(myPRList.get(entry - 1));
					feedback += "\n - " + entry + " cancellato";
				}
				else feedback += "\n - " + entry + " non cancellato";
			}

			return feedback;

		}

	}
	
	private boolean isAmministratore(final long chatID, final int userID) {
		final TelegramBot bot = TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN);
		final List<ChatMember> chatMembers = bot.execute( new GetChatAdministrators(chatID) ).administrators();
		for(int i=0; i<chatMembers.size(); i++)
			if (chatMembers.get(i).user().id() == userID)
				return true;
		return false;
	}
	
}