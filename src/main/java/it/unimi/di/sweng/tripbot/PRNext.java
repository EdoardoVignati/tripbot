package it.unimi.di.sweng.tripbot;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.pengrad.telegrambot.model.Message;

public class PRNext implements IFunctionality {

	@Override
	public String exec(Message message) {

		final String pattern = "/prossimo_ritrovo";

		final String testoMessaggio = message.text().trim();

		if (!testoMessaggio.equals(pattern))
			return "formato input non corretto";

		final String groupID = message.chat().id().toString();

		try {

			final List<PointOfInterest> myPRList = CurrentModel.getCurrentModel().getPointOfInterestList(groupID);

			final PointOfInterestDateComparator myComparator = new PointOfInterestDateComparator();

			Collections.sort(myPRList, myComparator);

			final PointOfInterest currentPRDate = new PointOfInterest(null, new Date(), null, null);

			final SimpleDateFormat formatterData = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			for (PointOfInterest tmp : myPRList)
				if (myComparator.compare(tmp, currentPRDate) >= 0)
					return "il prossimo punto di ritrovo e': " + tmp.name + " " + formatterData.format(tmp.meetDate);

		} catch (NoSuchElementException e) {

			return "non e' stato impostato alcun punto di ritrovo";

		}

		return "non sono previsti altri punti di ritrovo";

	}

}
