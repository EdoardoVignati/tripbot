package it.unimi.di.sweng.tripbot;

public class CommandParser {

	private final String command;

	public CommandParser(String command) {
		this.command = command;
	}

	public IFunctionality dispatcher() {
		switch (command) {
		case "set_punto_ritrovo":
			return new PRManager();
		case "prossimo_ritrovo":
			return new PRManager();
		case "programma":
			return new PRManager();
		default:
			return null;
		}
	}

}
