package com.projetpaparobin.documents.dao;

import java.io.File;

import com.projetpaparobin.documents.preferences.DAOPreferencesImpl;
import com.projetpaparobin.documents.preferences.PreferencesPOJO;

public class DAOExcelImpl extends DAOExcel {

	private static DAOExcelImpl instance;
	private PreferencesPOJO prefs;
		
	private DAOExcelImpl() {
		prefs = new DAOPreferencesImpl().getPrefs();
	}
	
	public static DAOExcelImpl getInstance() {
		if(instance == null) {
			instance = new DAOExcelImpl();
		}
		
		return instance;
	}
	
	@Override
	public File getExcelTemplate() {
		return new File(prefs.getExcelTemplatePath());
	}

	@Override
	public String getNbrExtinguishersSheetName() {
		return prefs.getNbrExtinguishersSheetName();
	}

	@Override
	public String getParcIndustrielleSheetName() {
		return prefs.getParcIndustrielleSheetName();
	}

	@Override
	public String getParcTertiaireSheetName() {
		return prefs.getParcTertiaireSheetName();
	}

	@Override
	public String getRecensementSheetName() {
		return prefs.getRecensementSheetName();
	}
	
}
