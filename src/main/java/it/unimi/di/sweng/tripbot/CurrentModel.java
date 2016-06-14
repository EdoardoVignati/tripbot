package it.unimi.di.sweng.tripbot;

import it.unimi.di.sweng.tripbot.database.Model;
import it.unimi.di.sweng.tripbot.model.SingletonModel;

public class CurrentModel {
	private static IModel CURRENT_MODEL = new Model();
	
	public static IModel getCurrentModel() {
		return CURRENT_MODEL;
	}
	
	public static void setSingletonModel() {
		CURRENT_MODEL = SingletonModel.INSTANCE;
	}
	public static void setPSQLModel() {
		CURRENT_MODEL = new Model();
	}
}
