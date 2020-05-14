package com.projetpaparobin.documents.output;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class FillActivityResponsePOJO {

	private int row, nbrSheets;
	private XSSFSheet sheet;
	
	public FillActivityResponsePOJO(int row, int nbrSheets, XSSFSheet sheet) {
		super();
		this.row = row;
		this.nbrSheets = nbrSheets;
		this.sheet = sheet;
	}

	public XSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getNbrSheets() {
		return nbrSheets;
	}

	public void setNbrSheets(int nbrSheets) {
		this.nbrSheets = nbrSheets;
	}
	
}
