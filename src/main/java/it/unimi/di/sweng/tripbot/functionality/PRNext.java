package it.unimi.di.sweng.tripbot.functionality;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.model.Message;

import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterest;
import it.unimi.di.sweng.tripbot.geolocalization.PointOfInterestDateComparator;
import it.unimi.di.sweng.tripbot.model.CurrentModel;

public class PRNext implements IFunctionality {

	@Override
	public String exec(Message message) {

		final String pattern = "/prossimoritrovo";

		final String testoMessaggio = message.text().trim();

		if (!testoMessaggio.contains(pattern))
			return "Formato input non corretto";

		final String groupID = message.chat().id().toString();

		try {

			final List<PointOfInterest> myPRList = CurrentModel.getCurrentModel().getPointOfInterestList(groupID);

			final PointOfInterestDateComparator myComparator = new PointOfInterestDateComparator();

			Collections.sort(myPRList, myComparator);

			final PointOfInterest currentPRDate = new PointOfInterest(null, new Date(), null, null);

			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			for (PointOfInterest tmp : myPRList)
				if (myComparator.compare(tmp, currentPRDate) >= 0)
					return "Prossimo ritrovo:\n[" + tmp.name + "\n" + formatterData.format(tmp.meetDate) + "](" +tmp.position.mapUrl+")";
			

		} catch (NoSuchElementException e) {

			return "Non e' stato impostato alcun punto di ritrovo";

		}

		return "Non sono previsti altri punti di ritrovo";

	}

}
