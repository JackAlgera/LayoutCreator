package com.projetpaparobin.documents.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.dao.DAOExcelImpl;
import com.projetpaparobin.zones.EZoneType;
import com.projetpaparobin.zones.Extinguisher;
import com.projetpaparobin.zones.Zone;
import com.projetpaparobin.zones.extinguishers.TypeExtinguisher;

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
	
	public void generateExcel(String ExcelPath) {
		createNewExcel(daoExcel.getExcelTemplate(), ExcelPath);
		inputStream = new createInputStream(ExcelPath)
		Workbook workbook = WorkbookFactory.create(inputStream);
		for (Zone zone : layoutHandler.getZones()) {
			
			 HashMap<TypeExtinguisher, Integer> extinguisherList = new HashMap<TypeExtinguisher, Integer>();
	            for (Extinguisher e: zone.getExtincteurs()) {
	                TypeExtinguisher typeExtinguisher = new TypeExtinguisher(e.getExtinguisherType(), e.getAnneeMiseEnService());

	                if(extinguisherList.containsKey(typeExtinguisher)) {
	                    extinguisherList.put(typeExtinguisher, extinguisherList.get(typeExtinguisher) + 1);
	                } else {
	                    extinguisherList.put(typeExtinguisher, 1);
	                }
	            }
	            
			switch (zone.getIdentifiant().getActivityType()) {
			case INDUSTRIELLE:
				Sheet sheet = workbook.getSheetAt(2);
				putInExcel(0,11, zone.getIdentifiant().getAreaNumber(),sheet);
				putInExcel( 5, 11, zone.getIdentifiant().getAreaSize(),sheet);
				putInExcel( 0, 11, zone.getIdentifiant().getAreaNumber(),sheet);
				int lineVariable = 11;
				for (Map.Entry<TypeExtinguisher, Integer> extinguisher : extinguisherList.entrySet()) {
					putInExcel(6, lineVariable, extinguisher.getValue(),sheet);
					putInExcel(7, lineVariable, extinguisher.getKey().getType(),sheet);	
					putInExcel(8, lineVariable, extinguisher.getKey().getFabricationYear(),sheet);	
					lineVariable = lineVariable +1;
				}
				
				
				break;
			case TERTIAIRE:
				
				
				
				
				break;
			}
			
			
		}
	}
		
	
	
	public void createNewExcel(String titleOfTemplate, String titleOfNewExcel) {
		// copie le excel template
		try {
			FileInputStream inputStream = new FileInputStream(new File(titleOfTemplate));
		    Workbook workbook = WorkbookFactory.create(inputStream);
		    inputStream.close();
		    FileOutputStream outputStream = new FileOutputStream(titleOfNewExcel);
		    workbook.write(outputStream);
		    workbook.close();
		    outputStream.close();
		}catch (IOException | EncryptedDocumentException ex) {
			System.out.println("ERROR");
			// TODO: better handle of exception
		}
	}
	
public void fileExcelLineParc(int line, Zone zone) {
		// remplie une ligne du fichier excel pour le parc (industriel ou tertiaire)
		if (zone.getIdentifiant().getActivityType()=="T") {
			int sheetNumber = 4;
		}
		else if (zone.getIdentifiant().getActivityType()=="I") {
			int sheetNumber = 3;
		} 

		if (zone.getIdentifiant().getActivityType()=="PG") {
			
		}
		else if (zone.getIdentifiant().getActivityType()=="PC")
	}
	
	public void fileExcelLineRecensement(int line) {
		// remplie une ligne du fichier excel pour le recensement
		
	}
	
	
	
	public FileInputStream createInputStream(String ExcelPath) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(ExcelPath));
		}
		catch (IOException | EncryptedDocumentException
	            ex) {
			ex.printStackTrace();
			System.out.println("ERROR on FileInputStream");
		}
		return inputStream;

	}
		
	public Workbook createWorkbook(FileInputStream inputStream){
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(inputStream);
			
		}
		catch (IOException | EncryptedDocumentException
	            ex) {
			ex.printStackTrace();
			System.out.println("ERROR");
			// TODO: better handle of exception
		}
		return workbook;
	}
	
	public Sheet selectSheet(int sheetNumber,Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(sheetNumber);	
		return sheet;
	}
	
	
	public void putInExcel(int columnPos, int rowPos,String dataToAdd, Sheet sheet) {
		    Cell cell;
		    Row row = sheet.getRow(rowPos);
		    if (row==null) {
		    	row = sheet.createRow(rowPos);		    	
		    }
		    cell = row.getCell(columnPos);
		    if(cell==null) {
		    	cell = row.createCell(columnPos);
		    }
		        cell.setCellValue(dataToAdd);

	}
	
	public void putInExcel(int columnPos, int rowPos,int dataToAdd, Sheet sheet) {
		    Cell cell;
		    Row row = sheet.getRow(rowPos);
		    if (row==null) {
		    	row = sheet.createRow(rowPos);		    	
		    }
		    cell = row.getCell(columnPos);
		    if(cell==null) {
		    	cell = row.createCell(columnPos);
		    }
		        cell.setCellValue(dataToAdd);
	}
	
	public void closeWorkbook(FileInputStream inputStream, Workbook workbook, String ExcelPath) {
		try {
			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(ExcelPath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		}
		catch (IOException | EncryptedDocumentException
	            ex) {
			ex.printStackTrace();
			System.out.println("ERROR");
			// TODO: better handle of exception
		}
	}
	
	
}
