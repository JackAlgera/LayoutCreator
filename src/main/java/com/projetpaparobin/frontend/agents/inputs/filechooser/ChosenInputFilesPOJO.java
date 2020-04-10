package com.projetpaparobin.frontend.agents.inputs.filechooser;

public class ChosenInputFilesPOJO {

	private int pageNbr;
	private String layoutPath;
	private String excelPath;
		
	public ChosenInputFilesPOJO(int pageNbr, String layoutPath, String excelPath) {
		this.pageNbr = pageNbr;
		this.layoutPath = layoutPath;
		this.excelPath = excelPath;
	}
	
	public int getPageNbr() {
		return pageNbr;
	}
	
	public String getLayoutPath() {
		return layoutPath;
	}
	
	public String getExcelPath() {
		return excelPath;
	}

}
