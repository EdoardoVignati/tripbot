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
		
		output.append("/setpuntoritrovo _luogo dd/mm/aaaa HH:MM_ : Imposta un nuovo punto di ritrovo\n");
		output.append("/prossimoritrovo : Visualizza le informazioni del prossimo punto di ritrovo impostato\n");
		output.append("/programma : Visualizza la lista di tutti i punti di ritrovo impostati\n");
		output.append("/remove : Visualizza i punto di ritrovo con un indice per selezionare quelli da cancellare\n");
		output.append("/remove _num1 ecc_  : Cancella i punti di ritrovo di indice _num1 ecc_\n");
		
		return output.toString();
		
	}

}
