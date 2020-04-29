package com.projetpaparobin.documents.preferences.dao;

import java.util.prefs.Preferences;

import com.projetpaparobin.Launcher;
import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.PreferencesPOJO;

public class DAOPreferencesImpl extends DAOPreferences {

	private static DAOPreferencesImpl instance;
		
	private DAOPreferencesImpl() {
	}
	
	public static DAOPreferencesImpl getInstance() {
		if(instance == null) {
			instance = new DAOPreferencesImpl();
		}
		
		return instance;
	}
	
	@Override
	public PreferencesPOJO getPrefs() {
		Preferences preferences = Preferences.userNodeForPackage(Launcher.class);		
		return new PreferencesPOJO(preferences);
	}

	@Override
	public void setPrefs(PreferencesPOJO prefs) {
		Preferences preferences = Preferences.userNodeForPackage(Launcher.class);	
		
		if(prefs != null) {
			prefs.getMap().forEach((key, val) -> {
				preferences.put(key, val);
			});
		} else {
			for (EPreferencesValues prefKey : EPreferencesValues.values()) {
				preferences.remove(prefKey.getKey());
			}
		}
	}
	
	public String getKeyValue(EPreferencesValues prefKey) {
		return getPrefs().getKeyValue(prefKey);
	}
}
