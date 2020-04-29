package com.projetpaparobin.documents.preferences;

public enum EPreferencesValues {

	EXCEL_TEMPLATE_PATH("excelTemplatePath", ""),
	LAYOUT_PDF_PATH("layoutPDFPath", ""),
	LAYOUT_PAGE_NUM("layoutPageNum", "1"),
	NBR_EXTINGUISHERS_SHEET_NAME("nbrExtinguishersSheetName", "dernière page"),
	PARC_INDUSTRIELLE_SHEET_NAME("parcIndustrielleSheetName", "Parc activité industrielle"),
	PARC_TERTIAIRE_SHEET_NAME("parcTertiaireSheetName", "Parc activité tertiaire"),
	RECENSEMENT_SHEET_NAME("recensementSheetName", "Recensement");
		
	private String key;
	private String defaultValue;

	private EPreferencesValues(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String getKey() {
		return key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

}
