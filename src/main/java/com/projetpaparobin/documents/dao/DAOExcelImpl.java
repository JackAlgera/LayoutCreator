package com.projetpaparobin.documents.dao;

import java.io.File;

public class DAOExcelImpl extends DAOExcel {

	private static DAOExcelImpl instance;

	public static String EXCEL_TEMPLATE_PATH = "./COM-Q-50035005-EdK.xlsm";
	public final String PARC_INDUSTRIELLE_SHEET_NAME = "Parc activité industrielle";
	public final String PARC_TERTIAIRE_SHEET_NAME = "Parc activité tertiaire";
	public final String NBR_EXTINGUISHERS_SHEET_NAME = "dernière page";
	public final String RECENSEMENT_SHEET_NAME = "Recensement";
		
	private DAOExcelImpl() {
	}
	
	public static DAOExcelImpl getInstance() {
		if(instance == null) {
			instance = new DAOExcelImpl();
		}
		
		return instance;
	}
	
	@Override
	public File getExcelTemplate() {
		return new File(EXCEL_TEMPLATE_PATH);
	}
	
}
