package com.projetpaparobin.documents.output;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.projetpaparobin.objects.extinguishers.EExtinguisherType;

public class ExtinguisherTypePositionHandler {

	private static String JOKER_TYPE_STRING = "X";
	private static String JOKER = "JOKER";
	private static int cellWidth = 2;
	
	private List<String> allTypes;
	private HashMap<String, ExcelPosition> typePositions;
	
	private int nbrJokers = 0;
	private int jokerIndex = 0;
	
	public ExtinguisherTypePositionHandler(XSSFSheet nbrExtinguishersSheet) {
		typePositions = new HashMap<String, ExcelPosition>();
		allTypes = Stream.of(EExtinguisherType.values()).map(t -> t.getName()).collect(Collectors.toList());
		fillPositionTypes(nbrExtinguishersSheet);
	}
	
	private void fillPositionTypes(XSSFSheet nbrExtinguishersSheet) {
		for (Row row : nbrExtinguishersSheet) {
			for (Cell cell : row) {
				switch (cell.getCellType()) {
				case STRING:
					String[] vals = cell.getStringCellValue().strip().split(" ");
					if(vals.length > 5) {
						break;
					}
					
					if(vals.length == 1 && vals[0].strip().equals(JOKER_TYPE_STRING)) {
						System.out.println(JOKER + nbrJokers + " : " + cell.getRowIndex() + " " + (cell.getColumnIndex() + cellWidth));
						typePositions.put(JOKER + nbrJokers, new ExcelPosition(cell.getRowIndex(), cell.getColumnIndex() + cellWidth));
						
						nbrJokers++;
					}
					
					for (int i = 0; i < vals.length; i++) {
						String val = vals[i];
						if(val.length() > 1 && allTypes.contains(val)) {
//							System.out.println(val + " : " + cell.getRowIndex() + " " + (cell.getColumnIndex() + cellWidth));
							typePositions.put(val, new ExcelPosition(cell.getRowIndex(), cell.getColumnIndex() + cellWidth));
						}
					}
					break;
				}
			}
		}		
		
		System.out.println("Found " + typePositions.size() + " different types !");
	}
	
	public boolean addNewType(XSSFSheet nbrExtinguishersSheet, String newExtinguisherType) {
		if(typePositions.get(newExtinguisherType) == null && jokerIndex < nbrJokers) {
			String jokerKey = JOKER + jokerIndex;			
			ExcelPosition jokerPos = typePositions.remove(jokerKey);
			typePositions.put(newExtinguisherType, jokerPos);					
			jokerIndex++;

			Row row = (nbrExtinguishersSheet.getRow(jokerPos.getRow()) == null) ? nbrExtinguishersSheet.createRow(jokerPos.getRow()) : nbrExtinguishersSheet.getRow(jokerPos.getRow());
			Cell cell = (row.getCell(jokerPos.getColumn() - cellWidth) == null) ? row.createCell(jokerPos.getColumn() - cellWidth) : row.getCell(jokerPos.getColumn() - cellWidth);
			
			cell.setCellValue(newExtinguisherType + " X");
			
			return true;
		}
		
		return false;
	}
	
	public ExcelPosition getPosition(String type) {
		return typePositions.get(type);
	}
}
