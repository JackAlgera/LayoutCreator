package com.projetpaparobin.documents.preferences;

public class PreferencesPOJO {

	private String excelTemplatePath, layoutPDFPath;
	private int layoutPageNum;

	private String nbrExtinguishersSheetName, parcIndustrielleSheetName, parcTertiaireSheetName, recensementSheetName;
	
	public PreferencesPOJO(String excelTemplatePath, String layoutPDFPath, int layoutPageNum,
			String nbrExtinguishersSheetName, String parcIndustrielleSheetName, String parcTertiaireSheetName,
			String recensementSheetName) {
		this.excelTemplatePath = excelTemplatePath;
		this.layoutPDFPath = layoutPDFPath;
		this.layoutPageNum = layoutPageNum;
		this.nbrExtinguishersSheetName = nbrExtinguishersSheetName;
		this.parcIndustrielleSheetName = parcIndustrielleSheetName;
		this.parcTertiaireSheetName = parcTertiaireSheetName;
		this.recensementSheetName = recensementSheetName;
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

	public String getNbrExtinguishersSheetName() {
		return nbrExtinguishersSheetName;
	}

	public void setNbrExtinguishersSheetName(String nbrExtinguishersSheetName) {
		this.nbrExtinguishersSheetName = nbrExtinguishersSheetName;
	}

	public String getParcIndustrielleSheetName() {
		return parcIndustrielleSheetName;
	}

	public void setParcIndustrielleSheetName(String parcIndustrielleSheetName) {
		this.parcIndustrielleSheetName = parcIndustrielleSheetName;
	}

	public String getParcTertiaireSheetName() {
		return parcTertiaireSheetName;
	}

	public void setParcTertiaireSheetName(String parcTertiaireSheetName) {
		this.parcTertiaireSheetName = parcTertiaireSheetName;
	}

	public String getRecensementSheetName() {
		return recensementSheetName;
	}

	public void setRecensementSheetName(String recensementSheetName) {
		this.recensementSheetName = recensementSheetName;
	}
	
	
}
