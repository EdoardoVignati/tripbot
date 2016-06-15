package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

public class PRRemove implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "/remove";

		final String testoMessaggio = message.text().trim();

		if (!testoMessaggio.contains(pattern))
			return "Formato input non corretto";

		final Chat myChat = message.chat();
		final Long chatID = myChat.id();
		final String groupID = chatID.toString();
		
		if ( myChat.type() != Chat.Type.Private && !Administrators.isAmministratore(chatID, message.from().id()) )
			return "Solo gli amministratori possono eliminare i punti di ritrovo";

		final IModel md = CurrentModel.getCurrentModel();
		List<PointOfInterest> myPRList=null;
		
		try{
			
			myPRList = md.getPointOfInterestList(groupID);
			
		}catch(NoSuchElementException e){
			
			System.err.println("Database vuoto");
			return "Non e' stato impostato alcun punto di ritrovo";
			
		}
		
		String[] indexSplit = testoMessaggio.split(" ");
		
		if (indexSplit.length == 1) {

			int i = 1;
			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String output = "Il programma del viaggio e' il seguente:\n";
			
			for (PointOfInterest tmp : myPRList) {
				output += i + " - " + tmp.name + " " + formatterData.format(tmp.meetDate) + "\n";
				i++;
			}
			
			return output;
			
		}
		
		final List<Integer> indexEntries = new ArrayList<Integer>();

		try {
			
			for (int j = 1; j < indexSplit.length; j++)
				indexEntries.add(Integer.parseInt(indexSplit[j]));
				
		} catch (NumberFormatException ex) {
			return "Formato input non corretto";
		}
			
		String feedback = "Punto di ritrovo:";

		for (Integer tmpIndex : indexEntries) {
			
			if ( tmpIndex <= myPRList.size() ) {
				md.removePointOfInterest(myPRList.get(tmpIndex - 1));
				feedback += "\n - " + tmpIndex + " cancellato";
			} else {
				feedback += "\n - " + tmpIndex + " non cancellato";
			}

		}

		return feedback;


	}
	
}