package com.projetpaparobin.documents.output;

public class ExcelPosition {

	private int row, column;
	
	public ExcelPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}
