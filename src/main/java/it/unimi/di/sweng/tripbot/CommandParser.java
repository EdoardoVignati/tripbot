package it.unimi.di.sweng.tripbot;

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
