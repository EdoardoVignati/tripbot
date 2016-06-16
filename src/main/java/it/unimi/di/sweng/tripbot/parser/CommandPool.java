package it.unimi.di.sweng.tripbot.parser;

import java.util.HashMap;
import java.util.Map;

import it.unimi.di.sweng.tripbot.functionality.IFunctionality;
import it.unimi.di.sweng.tripbot.functionality.PRAll;
import it.unimi.di.sweng.tripbot.functionality.PRNext;
import it.unimi.di.sweng.tripbot.functionality.PRRemove;
import it.unimi.di.sweng.tripbot.functionality.PRSet;
import it.unimi.di.sweng.tripbot.functionality.StartManager;

public class CommandPool {
	private Map<String, IFunctionality> commands;
	private static CommandPool instance = null;
	
	protected CommandPool() {
		commands = new HashMap<String, IFunctionality>();
		commands.put("start", new StartManager());
		commands.put("setpuntoritrovo", new PRSet());
		commands.put("prossimoritrovo", new PRNext());
		commands.put("programma", new PRAll());
		commands.put("remove", new PRRemove());
	}
	
	public static synchronized CommandPool getInstance() {
		if (instance == null)
			instance = new CommandPool();
		return instance;
	}
	
	public static synchronized Map<String, IFunctionality> getCommands() {
		return getInstance().getCommandMap();
	}
	
	private Map<String, IFunctionality> getCommandMap() {
		return new HashMap<String, IFunctionality>(commands);
	}
}
