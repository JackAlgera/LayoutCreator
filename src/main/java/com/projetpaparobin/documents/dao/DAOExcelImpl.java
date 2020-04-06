package com.projetpaparobin.documents.dao;

import java.io.File;

public class DAOExcelImpl implements DAOExcel {

	private static DAOExcelImpl instance;

	private static final String EXCEL_TEMPLATE_PATH = "./COM-Q-50035005-EdK.xlsm";
	public static final String PARC_INDUSTRIELLE_SHEET_NAME = "Parc activit� industrielle";
	public static final String PARC_TERTIAIRE_SHEET_NAME = "Parc activit� tertiaire";
	
	private DAOExcelImpl() {
	}
	
	public static DAOExcelImpl getInstance() {
		if(instance == null) {
			instance = new DAOExcelImpl();
		}
		
		return instance;
	}
	
	public File getExcelTemplate() {
		return new File(EXCEL_TEMPLATE_PATH);
	}
	
}
