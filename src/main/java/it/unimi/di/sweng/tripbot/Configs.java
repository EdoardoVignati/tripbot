package it.unimi.di.sweng.tripbot;

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

	private Configs() {
		PORT = Integer.parseInt(System.getenv("SERVER_PORT"));
		SERVER_TOKEN = System.getenv("TELEGRAM_SERVER_TOKEN");
		BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
		GOOGLE_TOKEN = System.getenv("GOOGLE_API_TOKEN");
	}

}
