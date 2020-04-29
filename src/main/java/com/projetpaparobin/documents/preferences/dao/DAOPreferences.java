package com.projetpaparobin.documents.preferences.dao;

import com.projetpaparobin.documents.preferences.PreferencesPOJO;

public abstract class DAOPreferences {

	public abstract PreferencesPOJO getPrefs();
	
	public abstract void setPrefs(PreferencesPOJO prefs);
	
}
