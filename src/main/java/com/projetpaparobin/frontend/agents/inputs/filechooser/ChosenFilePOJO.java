package com.projetpaparobin.frontend.agents.inputs.filechooser;

public class ChosenFilePOJO {

	private int pageNbr;
	private String filePath;
		
	public ChosenFilePOJO(int pageNbr, String filePath) {
		this.pageNbr = pageNbr;
		this.filePath = filePath;
	}
	public int getPageNbr() {
		return pageNbr;
	}
	public String getFilePath() {
		return filePath;
	}
	
}
