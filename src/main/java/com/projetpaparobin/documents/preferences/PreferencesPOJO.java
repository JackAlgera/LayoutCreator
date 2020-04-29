package com.projetpaparobin.documents.preferences;

import java.util.HashMap;
import java.util.prefs.Preferences;

public class PreferencesPOJO {
	
	private HashMap<String, String> map;
	
	public PreferencesPOJO() {
		this.map = new HashMap<String, String>();
	}
	
	public PreferencesPOJO(Preferences preferences) {
		this.map = new HashMap<String, String>();
		for (EPreferencesValues prefKey : EPreferencesValues.values()) {
			map.put(prefKey.getKey(), preferences.get(prefKey.getKey(), prefKey.getDefaultValue()));
		}
	}
	
	public HashMap<String, String> getMap() {
		return map;
	}
	
	public void addKeyValue(EPreferencesValues prefKey, String value) {
		map.put(prefKey.getKey(), value);
	}
	
	public String getKeyValue(EPreferencesValues prefKey) {
		return map.get(prefKey.getKey());
	}
	
}
