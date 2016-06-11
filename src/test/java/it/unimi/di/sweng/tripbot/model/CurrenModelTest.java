package it.unimi.di.sweng.tripbot.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import it.unimi.di.sweng.tripbot.CurrentModel;
import it.unimi.di.sweng.tripbot.database.Model;

public class CurrenModelTest {
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max

	@Test
	public void testSetSingletonModelAndSetPSQLModel() throws Exception {
		CurrentModel.setSingletonModel();
		assertEquals(CurrentModel.getCurrentModel().getClass(), SingletonModel.class);
		
		CurrentModel.setPSQLModel();
		assertEquals(CurrentModel.getCurrentModel().getClass(), Model.class);
	}

}
