package it.unimi.di.sweng.tripbot.model;

import it.unimi.di.sweng.tripbot.database.DatabaseModel;

public class CurrentModel {
	private static IModel CURRENT_MODEL = new DatabaseModel();
	
	public static IModel getCurrentModel() {
		return CURRENT_MODEL;
	}
	
	public static void setSingletonModel() {
		CURRENT_MODEL = SingletonModel.INSTANCE;
	}
	public static void setPSQLModel() {
		CURRENT_MODEL = new DatabaseModel();
	}
}
