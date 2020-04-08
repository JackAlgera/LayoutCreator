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

	private static int cellWidth = 2;
	
	private List<String> allTypes;
	private HashMap<String, ExcelPosition> typePositions;
	
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
					
					for (int i = 0; i < vals.length; i++) {
						String val = vals[i];
						if(val.length() > 1 && allTypes.contains(val)) {
							System.out.println(val + " : " + cell.getRowIndex() + " " + (cell.getColumnIndex() + cellWidth));
							typePositions.put(val, new ExcelPosition(cell.getRowIndex(), cell.getColumnIndex() + cellWidth));
						}
					}
					break;
				}
			}
		}
		System.out.println("Found " + typePositions.size() + " different types !");
	}
	
	public ExcelPosition getPosition(String type) {
		return typePositions.get(type);
	}
}
