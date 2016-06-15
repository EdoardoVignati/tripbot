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
		try {

			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			final List<PointOfInterest> myPRList = md.getPointOfInterestList(groupID);
			String[] indexEntries = message.text().split(" ");

			if (indexEntries.length == 1) {
				String output = "Il programma del viaggio e' il seguente:\n";
				for (PointOfInterest tmp : myPRList) {
					output += i + " - " + tmp.name + " " + formatterData.format(tmp.meetDate) + "\n";
					i++;
				}
				return output;
			} else {
				for (int j = 2; j < indexEntries.length; j++)
					md.removePointOfInterest(myPRList.get(Integer.parseInt(indexEntries[j]) - 1));

				return "Punto di ritrovo cancellato";
			}

		} catch (NoSuchElementException e) {

			return "Non e' stato trovato alcun punto di ritrovo";

		}

	}
}