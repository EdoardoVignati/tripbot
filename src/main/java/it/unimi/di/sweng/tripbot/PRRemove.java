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

			String output = "Il programma del viaggio e' il seguente:\n";

			for (PointOfInterest tmp : myPRList) {
				output += i + " - " + tmp.name + " " + formatterData.format(tmp.meetDate) + "\n";
				i++;
			}
			return output;

		} catch (NoSuchElementException e) {

			return "Non e' stato trovato alcun punto di ritrovo";

		}

	}
}