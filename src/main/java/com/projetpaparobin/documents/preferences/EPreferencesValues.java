package com.projetpaparobin.documents.preferences;

public enum EPreferencesValues {

	EXCEL_TEMPLATE_PATH("Excel Template Path :", "excelTemplatePath", ""),
	LAYOUT_PDF_PATH("PDF du plan :", "layoutPDFPath", ""),
	LAYOUT_PAGE_NUM("Num�ro de la page du plan :", "layoutPageNum", "1"),
	WORKSPACE_PATH("Espace de travail :", "workspacePath", ""),
	
	NBR_EXTINGUISHERS_SHEET_NAME("Nom de la page d'extincteurs :", "nbrExtinguishersSheetName", "derni�re page"),
	PARC_INDUSTRIELLE_SHEET_NAME("Nom de la page d'activit� industrielle :", "parcIndustrielleSheetName", "Parc activit� industrielle"),
	PARC_TERTIAIRE_SHEET_NAME("Nom de la page d'activit� tertiaire :", "parcTertiaireSheetName", "Parc activit� tertiaire"),
	RECENSEMENT_SHEET_NAME("Nom de la page de recensement :", "recensementSheetName", "Recensement"),
		
	MIN_TEXT_SIZE("Taille min des textes (0.0 < x < 1.0) :", "minTextSize", "0.02");
	
	private String key;
	private String defaultValue;
	private String displayValue;

	private EPreferencesValues(String displayValue, String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.displayValue = displayValue;
	}

	public String getKey() {
		return key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}

}
