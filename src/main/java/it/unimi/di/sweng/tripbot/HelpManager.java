package it.unimi.di.sweng.tripbot;

import com.pengrad.telegrambot.model.Message;

public class HelpManager implements IFunctionality {

	@Override
	public String exec(Message message) {
		
		final String pattern = "/help";
		
		final String testoMessaggio = message.text().trim();
	    
	    if ( ! testoMessaggio.contains(pattern) )
	    	return "comando non trovato";
		
		StringBuilder output = new StringBuilder();
		
		output.append("/set_punto_ritrovo luogo dd/mm/aaaa HH:MM : Imposta un nuovo punto di ritrovo\n");
		output.append("/prossimo_ritrovo : Visualizza le informazioni del prossimo punto di ritrovo impostato\n");
		output.append("/programma : Visualizza la lista di tutti i punti di ritrovo impostati\n");
		output.append("/remove : Visualizza i punto di ritrovo con un indice per selezionare quelli da cancellare\n");
		output.append("/remove <num1> <num2> ... : Cancella i punti di ritrovo di indice <num1> <num2> ...\n");
		
		return output.toString();
		
	}

}
