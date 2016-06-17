package it.unimi.di.sweng.tripbot.server;

import java.util.Locale;
/**
 * Contiene le configurazioni del <em>bot</em> lette dalle variabili d'ambiente.
 *
 */
public enum Configs {
	INSTANCE;

	public final int PORT;
	public final String SERVER_TOKEN;
	public final String BOT_TOKEN;
	public final String GOOGLE_TOKEN;
	public final String DB;

	private Configs() {
		Locale.setDefault(Locale.US);	
		PORT = Integer.parseInt(System.getenv("PORT"));
		SERVER_TOKEN = System.getenv("TELEGRAM_SERVER_TOKEN");
		BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
		GOOGLE_TOKEN = System.getenv("GOOGLE_API_TOKEN");
		DB = System.getenv("JDBC_DATABASE_URL");
	}

}
