package com.projetpaparobin.documents.preferences;

public class PreferencesPOJO {

	private String excelTemplatePath, layoutPDFPath;
	private int layoutPageNum;

	public PreferencesPOJO(String excelTemplatePath, String layoutPDFPath, int layoutPageNum) {
		this.layoutPageNum = layoutPageNum;
		this.excelTemplatePath = excelTemplatePath;
		this.layoutPDFPath = layoutPDFPath;
	}

	public String getExcelTemplatePath() {
		return excelTemplatePath;
	}

	public void setExcelTemplatePath(String excelTemplatePath) {
		this.excelTemplatePath = excelTemplatePath;
	}

	public String getLayoutPDFPath() {
		return layoutPDFPath;
	}

	public void setLayoutPDFPath(String layoutPDFPath) {
		this.layoutPDFPath = layoutPDFPath;
	}

	public int getLayoutPageNum() {
		return layoutPageNum;
	}

	public void setLayoutPageNum(int layoutPageNum) {
		this.layoutPageNum = layoutPageNum;
	}
	
}
