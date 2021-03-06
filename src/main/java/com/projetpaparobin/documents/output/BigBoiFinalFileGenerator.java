package com.projetpaparobin.documents.output;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.objects.extinguishers.EExtinguisherType;
import com.projetpaparobin.objects.extinguishers.EProtectionType;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.EActivityType;
import com.projetpaparobin.objects.zones.Zone;

public class BigBoiFinalFileGenerator {

	private static String DATE_FORMAT = "dd/MM/yyyy";
	
	private static ArrayList<String> NOT_MES_5_TYPES = new ArrayList<String>(Arrays.asList(
			EExtinguisherType.E6AEVF.getName(),
			EExtinguisherType.AL6F.getName(),
			EExtinguisherType.C2.getName(),
			EExtinguisherType.C5.getName(),
			EExtinguisherType.C10.getName(),
			EExtinguisherType.C20.getName(),
			EExtinguisherType.P25.getName(),
			EExtinguisherType.P25P.getName(),
			EExtinguisherType.P50.getName(),
			EExtinguisherType.P50P.getName(),
			EExtinguisherType.E45A.getName(),
			EExtinguisherType.E45A2T.getName()
			));
	
	private static ArrayList<String> TYPE_6L = new ArrayList<String>(Arrays.asList(
			EExtinguisherType.E6A.getName(),
			EExtinguisherType.E6AEVT.getName(),			
			EExtinguisherType.E6AEVP.getName(),
			EExtinguisherType.E6AEVF.getName(),
			EExtinguisherType.AL6F.getName(),
			EExtinguisherType.AL6S_30.getName(),
			EExtinguisherType.P6P.getName(),
			EExtinguisherType.P6.getName()
			));
	
	private static TabHandler tabHandler = TabHandler.getInstance();
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
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
		FileInputStream fileInputStream = null;
		XSSFWorkbook workbook = null;
		try {
			fileInputStream = new FileInputStream(dao.getKeyValue(EPreferencesValues.EXCEL_TEMPLATE_PATH));
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<Extinguisher> extinguishers = new ArrayList<Extinguisher>();

		XSSFSheet industrielleSheet = workbook.getSheet(dao.getKeyValue(EPreferencesValues.PARC_INDUSTRIELLE_SHEET_NAME));
		XSSFSheet tertiaireSheet = workbook.getSheet(dao.getKeyValue(EPreferencesValues.PARC_TERTIAIRE_SHEET_NAME));
		XSSFSheet nbrExtinguishersSheet = workbook.getSheet(dao.getKeyValue(EPreferencesValues.NBR_EXTINGUISHERS_SHEET_NAME));
		XSSFSheet recensementSheet = workbook.getSheet(dao.getKeyValue(EPreferencesValues.RECENSEMENT_SHEET_NAME));
		
		ArrayList<XSSFSheet> copiedSheets = new ArrayList<XSSFSheet>();
		XSSFSheet baseIndustrielleSheet = workbook.cloneSheet(workbook.getSheetIndex(industrielleSheet), industrielleSheet.getSheetName() + "_clone");
		XSSFSheet baseTertiaireSheet = workbook.cloneSheet(workbook.getSheetIndex(tertiaireSheet), tertiaireSheet.getSheetName() + "_clone");
		XSSFSheet baseRecensementSheet = workbook.cloneSheet(workbook.getSheetIndex(recensementSheet), recensementSheet.getSheetName() + "_clone");
		fillBaseRecensementSheet(baseRecensementSheet);

		copiedSheets.add(baseIndustrielleSheet);
		copiedSheets.add(baseTertiaireSheet);
		copiedSheets.add(baseRecensementSheet);
		
		int tertiaireRow = 11;
		int initTertiaireRow = tertiaireRow;
		int industrielleRow = 11;
		int initIndustrielleRow = industrielleRow;
		int initRecensementRow = 15;
		int recensementRow = initRecensementRow;
		
		ExtinguisherTypePositionHandler positionHandler = new ExtinguisherTypePositionHandler(nbrExtinguishersSheet);
		
		Integer nbrTertiaireSheets = 1;
		Integer maxExtinguishersTertiaireSheet = Integer.parseInt(dao.getKeyValue(EPreferencesValues.MAX_EXTINGUISHERS_TERTIAIRE_SHEET));
		Integer nbrIndustrielleSheets = 1;
		Integer maxExtinguishersIndustrielleSheet = Integer.parseInt(dao.getKeyValue(EPreferencesValues.MAX_EXTINGUISHERS_INDUSTRIELLE_SHEET));

		for (LayoutHandler layoutHandler : tabHandler.getLayoutHandlers()) {
			for (Zone zone : layoutHandler.getZones()) {		
				boolean containsPIP = false;
				HashMap<TypeExtinguisher, Integer> extinguisherList = new HashMap<TypeExtinguisher, Integer>();
	            for (Extinguisher e: zone.getExtinguishers()) {
	                TypeExtinguisher typeExtinguisher = new TypeExtinguisher(e);

	                if(extinguisherList.containsKey(typeExtinguisher)) {
	                    extinguisherList.put(typeExtinguisher, extinguisherList.get(typeExtinguisher) + 1);
	                } else {
	                    extinguisherList.put(typeExtinguisher, 1);
	                }
	                
	                extinguishers.add(e);
	                
	                if(e.getProtectionType().equals(EProtectionType.PIP.toString())) {
	                	containsPIP = true;
	                }
	            }
		            	            
				switch (zone.getId().getActivityType()) {
				case INDUSTRIELLE:	
					FillActivityResponsePOJO indusResponse = fillActivitySheet(dao.getKeyValue(EPreferencesValues.PARC_INDUSTRIELLE_SHEET_NAME), industrielleSheet, baseIndustrielleSheet, nbrIndustrielleSheets, industrielleRow, initIndustrielleRow, maxExtinguishersIndustrielleSheet, zone, extinguisherList, containsPIP);
					industrielleRow = indusResponse.getRow();
					nbrIndustrielleSheets = indusResponse.getNbrSheets();
					industrielleSheet = indusResponse.getSheet();
					break;
					
				case TERTIAIRE:
					FillActivityResponsePOJO tertRresponse = fillActivitySheet(dao.getKeyValue(EPreferencesValues.PARC_TERTIAIRE_SHEET_NAME), tertiaireSheet, baseTertiaireSheet, nbrTertiaireSheets, tertiaireRow, initTertiaireRow, maxExtinguishersTertiaireSheet, zone, extinguisherList, containsPIP);
					tertiaireRow = tertRresponse.getRow();
					nbrTertiaireSheets = tertRresponse.getNbrSheets();
					tertiaireSheet = tertRresponse.getSheet();
					break;
				}
			}
		}
		
		Collections.sort(extinguishers, new Comparator<Extinguisher>() {
			@Override
			public int compare(Extinguisher ex1, Extinguisher ex2) {
				return ex1.getNumber().compareTo(ex2.getNumber());
			}
		});
		
		int nbrExtinguishers = 0;
		int nbrRecensementPages = 1;
		int maxExtinguishersRecensementSheet = Integer.parseInt(dao.getKeyValue(EPreferencesValues.MAX_EXTINGUISHERS_RECENSEMENT_SHEET));
		for (Extinguisher ex : extinguishers) {
			if(nbrExtinguishers >= maxExtinguishersRecensementSheet) {
				nbrRecensementPages++;
				String newSheetName = dao.getKeyValue(EPreferencesValues.RECENSEMENT_SHEET_NAME) + nbrRecensementPages;
				
				XSSFSheet newSheet = workbook.cloneSheet(workbook.getSheetIndex(baseRecensementSheet), newSheetName);
				workbook.setSheetOrder(newSheetName, workbook.getSheetIndex(recensementSheet.getSheetName()) + 1);
				recensementSheet = newSheet;
				recensementRow = initRecensementRow;
				nbrExtinguishers = 0;
			}
			fillExtinguisherSheet(ex.getExtinguisherType(), nbrExtinguishersSheet, positionHandler);
			fillRecensementSheet(recensementRow, recensementSheet, ex);
			recensementRow++;
			nbrExtinguishers++;
		}
		closeProject(fileInputStream, outputTitle, workbook, copiedSheets);
	}
		
	private void fillTimeDates(XSSFSheet sheet) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		fillExcelCell(sheet, 17, 1, CellType.STRING, timeFormatter.format(LocalDateTime.now()));
	}
	
	private void setNbrPages(XSSFSheet sheet, int nbrPages) {
		fillExcelCell(sheet, 2, 19, CellType.NUMERIC, nbrPages);
	}
	
	private void fillRecensementSheet(int row, XSSFSheet sheet, Extinguisher ex) {
		fillExcelCell(sheet, row, 0, CellType.STRING, ex.getNumber());
		fillExcelCell(sheet, row, 2, CellType.STRING, ex.getZone().getId().getAreaName() + " " + ((ex.getLocal() == null) ? "" : ex.getLocal()));
		
		fillExcelCell(sheet, row, 9, CellType.STRING, ex.getZone().getId().getActivityTypeAbbreviation());	
		if(!ex.getProtectionType().equals(EProtectionType.PIP.toString())) {
			fillExcelCell(sheet, row, 10, CellType.NUMERIC, ex.getZone().getId().getAreaSize());
		}
		fillExcelCell(sheet, row, 12, CellType.STRING, ex.getExtinguisherType());
		fillExcelCell(sheet, row, 14, CellType.STRING, ex.getBrand());
		fillExcelCell(sheet, row, 16, CellType.NUMERIC, ex.getFabricationYear());
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if(!NOT_MES_5_TYPES.contains(ex.getExtinguisherType()) && (year - ex.getFabricationYear()) > 5) {
			fillExcelCell(sheet, row, 18, CellType.NUMERIC, ex.getFabricationYear() + 5);
		} 
		
	}
	
	private void fillExtinguisherSheet(String typeExtinguisher, XSSFSheet sheet, ExtinguisherTypePositionHandler positionHandler) {
		ExcelPosition pos = positionHandler.getPosition(typeExtinguisher);
		if(pos != null) {
			Row row = (sheet.getRow(pos.getRow()) == null) ? sheet.createRow(pos.getRow()) : sheet.getRow(pos.getRow());
			Cell cell = (row.getCell(pos.getColumn()) == null) ? row.createCell(pos.getColumn()) : row.getCell(pos.getColumn());
			double val = 0;
			switch (cell.getCellType()) {
			case NUMERIC:
				val = cell.getNumericCellValue() + 1.0;
				break;
			case STRING:
				val = (cell.getStringCellValue().isBlank()) ? 1.0 : Double.parseDouble(cell.getStringCellValue()) + 1.0;
				break;
			default:
				val = 1.0;
				break;
			}
			cell.setCellValue(val);
		} else if(positionHandler.addNewType(sheet, typeExtinguisher)) {
			fillExtinguisherSheet(typeExtinguisher, sheet, positionHandler);
		}
	}
	
	private void addTopBorderToCells(int rowNbr, int startColumn, int finalColumn, XSSFSheet sheet) {
		Row row = (sheet.getRow(rowNbr) == null) ? sheet.createRow(rowNbr) : sheet.getRow(rowNbr);
		for(int i = startColumn; i <= finalColumn; i++) {
			Cell cell = (row.getCell(i) == null) ? row.createCell(i) : row.getCell(i);
			CellStyle style = ((XSSFCell) cell).getCellStyle().copy();
			style.setBorderTop(BorderStyle.THICK);
			style.setTopBorderColor(IndexedColors.BLACK1.getIndex());			
			cell.setCellStyle(style);
		}
	}
	
	private FillActivityResponsePOJO fillActivitySheet(String sheetName, XSSFSheet sheet, XSSFSheet baseSheet, Integer nbrSheets, int rowNbr, int initRowNbr, int maxRowNbr, Zone zone, HashMap<TypeExtinguisher, Integer> extinguisherList, boolean containsPIP) {
		int rowNbrPG = rowNbr;
		int rowNbrPIP = rowNbr;
		
		fillExcelCell(sheet, rowNbrPIP, 0, CellType.NUMERIC, zone.getId().getAreaNumber());
		if(containsPIP) {
			fillExcelCell(sheet, rowNbrPIP, 11, CellType.STRING, zone.getId().getAreaName() + " " + zone.getId().getAreaSize() + zone.getId().getUnits().toString());
		} else {
			fillExcelCell(sheet, rowNbrPIP, 1, CellType.STRING, zone.getId().getAreaName());
			fillExcelCell(sheet, rowNbrPIP, 5, CellType.NUMERIC, zone.getId().getAreaSize());
		}
		
		for (Map.Entry<TypeExtinguisher, Integer> extinguisher : extinguisherList.entrySet()) {		
			switch (extinguisher.getKey().getProtectionType()) {
			case PC:
				fillExcelCell(sheet, rowNbrPIP, 11, CellType.STRING, extinguisher.getKey().getLocal());
			case PIP:
				fillExcelCell(sheet, rowNbrPIP, 17, CellType.NUMERIC, extinguisher.getValue());
				fillExcelCell(sheet, rowNbrPIP, 18, CellType.STRING, extinguisher.getKey().getType());
				fillExcelCell(sheet, rowNbrPIP, 20, CellType.NUMERIC, extinguisher.getKey().getFabricationYear());
				rowNbrPIP++;
				break;
			case PG:
				double FC = zone.getId().getActivityType().equals(EActivityType.TERTIAIRE) ? 1.0 : ((TYPE_6L.contains(extinguisher.getKey().getType())) ? 0.75 : 1.0);
				fillExcelCell(sheet, rowNbrPG, 6, CellType.NUMERIC, extinguisher.getValue());
				fillExcelCell(sheet, rowNbrPG, 7, CellType.STRING, extinguisher.getKey().getType());
				fillExcelCell(sheet, rowNbrPG, 8, CellType.NUMERIC, extinguisher.getKey().getFabricationYear());
				fillExcelCell(sheet, rowNbrPG, 9, CellType.NUMERIC, FC);
				rowNbrPG++;
				break;
			}		
			
			int maxRow = Math.max(rowNbrPG, rowNbrPIP);
			
			if((maxRow - initRowNbr) >= maxRowNbr) {
				addTopBorderToCells(maxRow, 1, 20, sheet);
				
				nbrSheets++;
				String newSheetName = sheetName + nbrSheets;
				
				XSSFSheet newSheet = sheet.getWorkbook().cloneSheet(sheet.getWorkbook().getSheetIndex(baseSheet), newSheetName);
				sheet.getWorkbook().setSheetOrder(newSheetName, sheet.getWorkbook().getSheetIndex(sheet.getSheetName()) + 1);
				sheet = newSheet;
				rowNbrPG = initRowNbr;
				rowNbrPIP = initRowNbr;
			}
		}
		
		if(extinguisherList.size() == 0) {
			rowNbrPIP++;
			rowNbrPG++;
			
			int maxRow = Math.max(rowNbrPG, rowNbrPIP);
			
			if((maxRow - initRowNbr) >= maxRowNbr) {
				addTopBorderToCells(maxRow, 1, 20, sheet);
				
				nbrSheets++;
				String newSheetName = sheetName + nbrSheets;

				XSSFSheet newSheet = sheet.getWorkbook().cloneSheet(sheet.getWorkbook().getSheetIndex(baseSheet), newSheetName);
				sheet.getWorkbook().setSheetOrder(newSheetName, sheet.getWorkbook().getSheetIndex(sheet.getSheetName()) + 1);
				sheet = newSheet;
				rowNbrPG = initRowNbr;
				rowNbrPIP = initRowNbr;
			}
		} 
		
		int maxRow = Math.max(rowNbrPG, rowNbrPIP);
		addTopBorderToCells(maxRow, 1, 20, sheet);
		return new FillActivityResponsePOJO(maxRow, nbrSheets, sheet);
	}
	
	private void fillBaseRecensementSheet(XSSFSheet sheet) {
		fillExcelCell(sheet, 5, 16, CellType.FORMULA, dao.getKeyValue(EPreferencesValues.RECENSEMENT_SHEET_NAME) + "!Q6");
	}
	
	private void fillExcelCell(XSSFSheet sheet, int rowNbr, int columnNbr, CellType cellType, Object data) {
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
		case FORMULA:
			cell.setCellFormula((String) data);
			break;
		default:
			break;
		}
	}
			
	private void closeProject(FileInputStream fileInputStream, String outputName, XSSFWorkbook workbook, ArrayList<XSSFSheet> copiedSheets) {
		try {			
			fillTimeDates(workbook.getSheet(dao.getKeyValue(EPreferencesValues.NBR_EXTINGUISHERS_SHEET_NAME)));
			
			for (XSSFSheet copiedSheet : copiedSheets) {
				workbook.removeSheetAt(workbook.getSheetIndex(copiedSheet));
			}
			setNbrPages(workbook.getSheet(dao.getKeyValue(EPreferencesValues.NBR_EXTINGUISHERS_SHEET_NAME)), workbook.getNumberOfSheets());
			XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
			
			fileInputStream.close();
			FileOutputStream outputStream = new FileOutputStream(outputName);
			workbook.write(outputStream);
			workbook.close();	
			outputStream.close();
			
			Desktop.getDesktop().open(new File(outputName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
