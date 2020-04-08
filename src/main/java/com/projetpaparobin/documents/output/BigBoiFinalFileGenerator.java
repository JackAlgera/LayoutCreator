package com.projetpaparobin.documents.output;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.dao.DAOExcelImpl;
import com.projetpaparobin.objects.extinguishers.EExtinguisherType;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.extinguishers.TypeExtinguisher;
import com.projetpaparobin.objects.zones.Zone;

public class BigBoiFinalFileGenerator {

	private static ArrayList<String> NOT_MES_CQ_TYPES = new ArrayList<String>(Arrays.asList(
			EExtinguisherType.E6AEVF.getName(),
			EExtinguisherType.AL6F.getName(),
			EExtinguisherType.C2.getName(),
			EExtinguisherType.C5.getName(),
			EExtinguisherType.P25.getName(),
			EExtinguisherType.P25P.getName(),
			EExtinguisherType.P50.getName(),
			EExtinguisherType.P50P.getName(),
			EExtinguisherType.C10.getName(),
			EExtinguisherType.C20.getName(),
			EExtinguisherType.E45A.getName(),
			EExtinguisherType.E45A2T.getName()
			));
	
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
		FileInputStream fileInputStream = null;
		XSSFWorkbook workbook = null;
		try {
			fileInputStream = new FileInputStream(dao.getExcelTemplate());
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<Extinguisher> extinguishers = new ArrayList<Extinguisher>();;
		
		XSSFSheet industrielleSheet = workbook.getSheet(dao.PARC_INDUSTRIELLE_SHEET_NAME);
		XSSFSheet tertiaireSheet = workbook.getSheet(dao.PARC_TERTIAIRE_SHEET_NAME);
		XSSFSheet nbrExtinguishersSheet = workbook.getSheet(dao.NBR_EXTINGUISHERS_SHEET_NAME);
		XSSFSheet recensementSheet = workbook.getSheet(dao.RECENSEMENT_SHEET_NAME);
		
		int tertiaireRow = 11;
		int industrielleRow = 11;
		int recensementRow = 15;
		
		ExtinguisherTypePositionHandler positionHandler = new ExtinguisherTypePositionHandler(nbrExtinguishersSheet);
		
		for (Zone zone : layoutHandler.getZones()) {			
			HashMap<TypeExtinguisher, Integer> extinguisherList = new HashMap<TypeExtinguisher, Integer>();
            for (Extinguisher e: zone.getExtinguishers()) {
                TypeExtinguisher typeExtinguisher = new TypeExtinguisher(e.getId().getExtinguisherType(), e.getId().getFabricationYear());

                if(extinguisherList.containsKey(typeExtinguisher)) {
                    extinguisherList.put(typeExtinguisher, extinguisherList.get(typeExtinguisher) + 1);
                } else {
                    extinguisherList.put(typeExtinguisher, 1);
                }
                
                extinguishers.add(e);
            }
	            	            
			switch (zone.getId().getActivityType()) {
			case INDUSTRIELLE:				
				fillExcelSheet(industrielleSheet, tertiaireRow, 0, CellType.NUMERIC, zone.getId().getAreaNumber());
				fillExcelSheet(industrielleSheet, tertiaireRow, 5, CellType.NUMERIC, zone.getId().getAreaSize());
				
				for (Map.Entry<TypeExtinguisher, Integer> extinguisher : extinguisherList.entrySet()) {
					fillExcelSheet(industrielleSheet, tertiaireRow, 6, CellType.NUMERIC, extinguisher.getValue());
					fillExcelSheet(industrielleSheet, tertiaireRow, 7, CellType.STRING, extinguisher.getKey().getType());
					fillExcelSheet(industrielleSheet, tertiaireRow, 8, CellType.NUMERIC, extinguisher.getKey().getFabricationYear());
					// TODO : Get value for FC
					fillExcelSheet(industrielleSheet, tertiaireRow, 9, CellType.NUMERIC, 1.0);
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
					fillExcelSheet(tertiaireSheet, industrielleRow, 9, CellType.NUMERIC, 1.0);
					industrielleRow++;
				}
				break;
			}
		}

		for (Extinguisher ex : extinguishers) {
			fillExtinguisherSheet(ex.getId().getExtinguisherType(), nbrExtinguishersSheet, positionHandler);
			fillRecensementSheet(recensementRow, recensementSheet, ex);
			recensementRow++;
		}
		
		System.out.println(workbook.getSheet(dao.NBR_EXTINGUISHERS_SHEET_NAME).getRow(10).getCell(1).getNumericCellValue());
		for (CellRangeAddress mergedCell : nbrExtinguishersSheet.getMergedRegions()) {
			if(mergedCell.isInRange(10, 1)) {
				System.out.println(mergedCell.getNumberOfCells());
			}
		}
		closeProject(fileInputStream, outputTitle, workbook);
	}
	
	private void fillRecensementSheet(int row, XSSFSheet sheet, Extinguisher ex) {
		fillExcelSheet(sheet, row, 0, CellType.STRING, ex.getId().getNumber());
		fillExcelSheet(sheet, row, 10, CellType.NUMERIC, ex.getZone().getShape().getAreaSize());
		fillExcelSheet(sheet, row, 14, CellType.STRING, ex.getId().getBrand());
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if(!NOT_MES_CQ_TYPES.contains(ex.getId().getExtinguisherType()) && (year - ex.getId().getFabricationYear()) > 5) {
			fillExcelSheet(sheet, row, 18, CellType.STRING, "MES+5");
		}
		
	}
	
	private void fillExtinguisherSheet(String typeExtinguisher, XSSFSheet sheet, ExtinguisherTypePositionHandler positionHandler) {
		ExcelPosition pos = positionHandler.getPosition(typeExtinguisher);
		if(pos != null) {
			Row row = (sheet.getRow(pos.getRow()) == null) ? sheet.createRow(pos.getRow()) : sheet.getRow(pos.getRow());
			Cell cell = (row.getCell(pos.getColumn()) == null) ? row.createCell(pos.getColumn()) : row.getCell(pos.getColumn());
			cell.setCellValue(cell.getNumericCellValue() + 1);
		}
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
