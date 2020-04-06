package com.projetpaparobin.documents.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.dao.DAOExcelImpl;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.extinguishers.TypeExtinguisher;
import com.projetpaparobin.objects.zones.Zone;

public class BigBoiFinalFileGenerator {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static DAOExcelImpl dao = DAOExcelImpl.getInstance();
	
	private static BigBoiFinalFileGenerator instance;
	
	private BigBoiFinalFileGenerator() {			
	}
	
	public static BigBoiFinalFileGenerator getInstance() {
		if(instance == null) {
			instance = new BigBoiFinalFileGenerator();
		}
		
		return instance;
	}
	
	public void generateExcel(String outputTitle) {
//		createNewExcel(dao.getExcelTemplate(), outputTitle);
		FileInputStream fileInputStream = null;
		XSSFWorkbook workbook = null;
		try {
			fileInputStream = new FileInputStream(dao.getExcelTemplate());
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		XSSFSheet industrielleSheet = workbook.getSheet(dao.PARC_INDUSTRIELLE_SHEET_NAME);
		XSSFSheet tertiaireSheet = workbook.getSheet(dao.PARC_TERTIAIRE_SHEET_NAME);
		
		int tertiaireRow = 11;
		int industrielleRow = 11;
		
		for (Zone zone : layoutHandler.getZones()) {			
			HashMap<TypeExtinguisher, Integer> extinguisherList = new HashMap<TypeExtinguisher, Integer>();
            for (Extinguisher e: zone.getExtinguishers()) {
                TypeExtinguisher typeExtinguisher = new TypeExtinguisher(e.getId().getExtinguisherType(), e.getId().getFabricationYear());

                if(extinguisherList.containsKey(typeExtinguisher)) {
                    extinguisherList.put(typeExtinguisher, extinguisherList.get(typeExtinguisher) + 1);
                } else {
                    extinguisherList.put(typeExtinguisher, 1);
                }
            }
	            	            
			switch (zone.getId().getActivityType()) {
			case INDUSTRIELLE:				
				fillExcelSheet(industrielleSheet, tertiaireRow, 0, CellType.NUMERIC, zone.getId().getAreaNumber());
				fillExcelSheet(industrielleSheet, tertiaireRow, 5, CellType.NUMERIC, zone.getId().getAreaSize());
				
				for (Map.Entry<TypeExtinguisher, Integer> extinguisher : extinguisherList.entrySet()) {
					fillExcelSheet(industrielleSheet, tertiaireRow, 6, CellType.NUMERIC, extinguisher.getValue());
					fillExcelSheet(industrielleSheet, tertiaireRow, 7, CellType.STRING, extinguisher.getKey().getType());
					fillExcelSheet(industrielleSheet, tertiaireRow, 8, CellType.NUMERIC, extinguisher.getKey().getFabricationYear());
					tertiaireRow++;
				}
				break;
				
			case TERTIAIRE:
				fillExcelSheet(tertiaireSheet, industrielleRow, 0, CellType.NUMERIC, zone.getId().getAreaNumber());
				fillExcelSheet(tertiaireSheet, industrielleRow, 5, CellType.NUMERIC, zone.getId().getAreaSize());
				
				for (Map.Entry<TypeExtinguisher, Integer> extinguisher : extinguisherList.entrySet()) {
					fillExcelSheet(tertiaireSheet, industrielleRow, 6, CellType.NUMERIC, extinguisher.getValue());
					fillExcelSheet(tertiaireSheet, industrielleRow, 7, CellType.STRING, extinguisher.getKey().getType());
					fillExcelSheet(tertiaireSheet, industrielleRow, 8, CellType.NUMERIC, extinguisher.getKey().getFabricationYear());
					industrielleRow++;
				}
				break;
			}
		}
		
		closeProject(fileInputStream, outputTitle, workbook);
	}
	
	private void fillExcelSheet(XSSFSheet sheet, int rowNbr, int columnNbr, CellType cellType, Object data) {
		Row row = (sheet.getRow(rowNbr) == null) ? sheet.createRow(rowNbr) : sheet.getRow(rowNbr);
		Cell cell = (row.getCell(columnNbr) == null) ? row.createCell(columnNbr) : row.getCell(columnNbr);
		
		switch (cellType) {
		case BOOLEAN:
			cell.setCellValue((boolean) data);
			break;
		case NUMERIC:
			if(data.getClass().equals(Integer.class)) {
				cell.setCellValue(((Integer) data).doubleValue());
			} else {
				cell.setCellValue((double) data);
			}
			break;
		case STRING:
			cell.setCellValue((String) data);
			break;
		case _NONE:
			cell.setCellValue((String) data);
			break;
		default:
			break;
		}
	}
	
	private void createNewExcel(File templateFile, String outputTitle) {
	    try {
			FileInputStream fileInputStream = new FileInputStream(templateFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			fileInputStream.close();
			
		    FileOutputStream fileOutputStream = new FileOutputStream(outputTitle);
		    workbook.write(fileOutputStream);
		    workbook.close();
		    fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fileExcelLineRecensement(int line) {
		// remplie une ligne du fichier excel pour le recensement
		
	}
	
	private void closeProject(FileInputStream fileInputStream, String outputName, XSSFWorkbook workbook) {
		try {
			fileInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(outputName);
			workbook.write(outputStream);
			workbook.close();	
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}
