package com.projetpaparobin.documents.preferences;

import java.util.prefs.Preferences;

import com.projetpaparobin.Launcher;

public class DAOPreferencesImpl extends DAOPreferences {

	@Override
	public PreferencesPOJO getPrefs() {
		Preferences pres = Preferences.userNodeForPackage(Launcher.class);		
		return new PreferencesPOJO(
				pres.get("excelTemplatePath", ""), 
				pres.get("layoutPDFPath", ""), 
				pres.getInt("layoutPageNum", -1),
				pres.get("nbrExtinguishersSheetName", ""),
				pres.get("parcIndustrielleSheetName", ""), 
				pres.get("parcTertiaireSheetName", ""),
				pres.get("recensementSheetName", ""));
	}

	@Override
	public void setPrefs(PreferencesPOJO prefs) {
		Preferences pres = Preferences.userNodeForPackage(Launcher.class);	
		
		if(prefs != null) {
			pres.put("excelTemplatePath", prefs.getExcelTemplatePath());
			pres.put("layoutPDFPath", prefs.getLayoutPDFPath());
			pres.putInt("layoutPageNum", prefs.getLayoutPageNum());
			pres.put("nbrExtinguishersSheetName", prefs.getNbrExtinguishersSheetName());
			pres.put("parcIndustrielleSheetName", prefs.getParcIndustrielleSheetName());
			pres.put("parcTertiaireSheetName", prefs.getParcTertiaireSheetName());
			pres.put("recensementSheetName", prefs.getRecensementSheetName());
		} else {
			pres.remove("excelTemplatePath");
			pres.remove("layoutPDFPath");
			pres.remove("layoutPageNum");
			pres.remove("nbrExtinguishersSheetName");
			pres.remove("parcIndustrielleSheetName");
			pres.remove("parcTertiaireSheetName");
			pres.remove("recensementSheetName");
		}
	}

}
