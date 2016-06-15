package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.model.Message;

public class PRRemove implements IFunctionality {

	@Override
	public String exec(Message message) {

		IModel md = CurrentModel.getCurrentModel();

		final String groupID = message.chat().id().toString();

		int i = 1;

			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			List<PointOfInterest> myPRList=null;
			try{
				myPRList = md.getPointOfInterestList(groupID);
			}catch(NoSuchElementException e){
				System.err.println("Database vuoto");
			}
			String[] indexEntries = message.text().split(" ");
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
					int entry = Integer.parseInt(indexEntries[j]);
					
					if ((myPRList!=null)&&(entry <= myPRList.size()))
						{
							md.removePointOfInterest(myPRList.get(entry - 1));
							feedback += "\n - " + entry + " cancellato";
						}
					else feedback += "\n - " + entry + " non cancellato";
				}

				return feedback;

			}

	}
}