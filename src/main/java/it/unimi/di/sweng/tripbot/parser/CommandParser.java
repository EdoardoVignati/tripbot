package it.unimi.di.sweng.tripbot.parser;

import it.unimi.di.sweng.tripbot.functionality.HelpManager;
import it.unimi.di.sweng.tripbot.functionality.IFunctionality;

public class CommandParser {

	private final String command;

	public CommandParser(String command) {
		this.command = command;
	}

	public IFunctionality dispatcher() {
		if (!CommandPool.getCommands().containsKey(command))
			return new HelpManager();

		return CommandPool.getCommands().get(command);
	}

}
