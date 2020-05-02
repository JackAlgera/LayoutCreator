package com.projetpaparobin.documents.preferences;

public enum EPreferencesValues {

	EXCEL_TEMPLATE_PATH("Excel Template Path :", "excelTemplatePath", ""),
	LAYOUT_PDF_PATH("PDF du plan :", "layoutPDFPath", ""),
	LAYOUT_PAGE_NUM("Numéro de la page du plan :", "layoutPageNum", "1"),
	WORKSPACE_PATH("Espace de travail :", "workspacePath", ""),
	
	NBR_EXTINGUISHERS_SHEET_NAME("layoutPageNum", "nbrExtinguishersSheetName", "dernière page"),
	PARC_INDUSTRIELLE_SHEET_NAME("layoutPageNum", "parcIndustrielleSheetName", "Parc activité industrielle"),
	PARC_TERTIAIRE_SHEET_NAME("layoutPageNum", "parcTertiaireSheetName", "Parc activité tertiaire"),
	RECENSEMENT_SHEET_NAME("layoutPageNum", "recensementSheetName", "Recensement");
		
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
