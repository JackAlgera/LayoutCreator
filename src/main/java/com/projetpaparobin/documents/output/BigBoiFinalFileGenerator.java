package com.projetpaparobin.documents.output;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.dao.DAOExcelImpl;

public class BigBoiFinalFileGenerator {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static DAOExcelImpl daoExcel = DAOExcelImpl.getInstance();
	
	private static BigBoiFinalFileGenerator instance;
	
	private BigBoiFinalFileGenerator() {	
	}
	
	public static BigBoiFinalFileGenerator getInstance() {
		if(instance == null) {
			instance = new BigBoiFinalFileGenerator();
		}
		
		return instance;
	}
	
	public void generateExcel() {
		
	}
		
	
}
