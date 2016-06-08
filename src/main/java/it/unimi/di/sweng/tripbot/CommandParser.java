package it.unimi.di.sweng.tripbot;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {

	private final String command;

	public CommandParser(String command) {
		this.command = command;
	}

	public IFunctionality dispatcher() {
		Map<String, IFunctionality> dispatcher = new HashMap<String, IFunctionality>();

		dispatcher.put("start", new StartManager());
		dispatcher.put("set_punto_ritrovo", new PRSet());
		dispatcher.put("prossimo_ritrovo", new PRNext());
		dispatcher.put("programma", new PRAll());
		dispatcher.put("sos", new SOS());

		if (!dispatcher.containsKey(command))
			return new HelpManager();

		return dispatcher.get(command);
	}

}
