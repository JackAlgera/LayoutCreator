package com.projetpaparobin.documents.dao;

import java.io.File;

import org.apache.pdfbox.multipdf.Splitter;

import com.projetpaparobin.documents.PDFHandler;

public class DAOExcelImpl implements DAOExcel {

	private static DAOExcelImpl instance;
	private static Splitter splitter = new Splitter();

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
		// TODO Auto-generated method stub
		return null;
	}
	
}
