package it.unimi.di.sweng.tripbot;

import com.pengrad.telegrambot.model.Message;

public class HelpManager implements IFunctionality {

	@Override
	public String exec(Message dumpMessage) {
		
		StringBuilder output = new StringBuilder();
		
		output.append("/set_punto_ritrovo luogo dd/mm/aaaa HH:MM : Imposta un nuovo punto di ritrovo");
		output.append("\n");
		output.append("/prossimo_ritrovo : Visualizza le informazioni del prossimo punto di ritrovo impostato");
		output.append("\n");
		output.append("/programma : Visualizza la lista di tutti i punti di ritrovo impostati");		
		output.append("\n");
		
		return output.toString();
		
	}

}
