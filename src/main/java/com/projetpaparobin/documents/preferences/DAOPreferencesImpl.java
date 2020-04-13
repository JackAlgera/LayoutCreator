package com.projetpaparobin.documents.preferences;

import java.util.prefs.Preferences;

import com.projetpaparobin.Launcher;

public class DAOPreferencesImpl extends DAOPreferences {

	@Override
	public PreferencesPOJO getPrefs() {
		Preferences pres = Preferences.userNodeForPackage(Launcher.class);		
		return new PreferencesPOJO(pres.get("excelTemplatePath", ""), pres.get("layoutPDFPath", ""), pres.getInt("layoutPageNum", -1));
	}

	@Override
	public void setPrefs(PreferencesPOJO prefs) {
		Preferences pres = Preferences.userNodeForPackage(Launcher.class);	
		
		if(prefs != null) {
			pres.put("excelTemplatePath", prefs.getExcelTemplatePath());
			pres.put("layoutPDFPath", prefs.getLayoutPDFPath());
			pres.putInt("layoutPageNum", prefs.getLayoutPageNum());
		} else {
			pres.remove("excelTemplatePath");
			pres.remove("layoutPDFPath");
			pres.remove("layoutPageNum");
		}
	}

}
