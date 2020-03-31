package com.projetpaparobin.documents.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
		
	public void PutInExcel(String excelFilePath, int columnPos, int rowPos, int sheetNumber, String dataToAdd) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		    Workbook workbook = WorkbookFactory.create(inputStream);
		    Sheet sheet = workbook.getSheetAt(sheetNumber);	
		    Row row = sheet.getRow(rowPos);
		    Cell cell = row.getCell(columnPos);
		    if (dataToAdd instanceof String) {
		        cell.setCellValue((String) dataToAdd);
		    } 
		    inputStream.close();
		    FileOutputStream outputStream = new FileOutputStream("testmethod.xlsx");
		    workbook.write(outputStream);
		    workbook.close();
		    outputStream.close();
		}
		catch (IOException | EncryptedDocumentException
	            ex) {
			System.out.println("ERROR");
			// TODO: better handle of exception
		}
	}
}
