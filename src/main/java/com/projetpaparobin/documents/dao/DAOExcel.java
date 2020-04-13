package com.projetpaparobin.documents.dao;

import java.io.File;

public abstract class DAOExcel {

	public abstract File getExcelTemplate();
	
	public abstract String getNbrExtinguishersSheetName();
	
	public abstract String getParcIndustrielleSheetName();
	
	public abstract String getParcTertiaireSheetName();
	
	public abstract String getRecensementSheetName();
	
}
