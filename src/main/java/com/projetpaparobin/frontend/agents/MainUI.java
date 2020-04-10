package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.documents.dao.DAOExcelImpl;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;

import javafx.scene.layout.HBox;

public class MainUI extends HBox {

	private PresentationLayoutAgent layoutPres;
	private ViewLayoutAgent layoutView;
	private SideBarAgent sideBar;
	
	public MainUI(int height, int width, String layoutPath, int imageNbr, String excelPath) {
		super();
		this.setPrefSize(width, height);	
		DAOExcelImpl.EXCEL_TEMPLATE_PATH = excelPath;
		
		layoutPres = new PresentationLayoutAgent();
		layoutView = new ViewLayoutAgent(layoutPath, imageNbr, height, width / 2, layoutPres);
		layoutPres.setView(layoutView);		

		sideBar = new SideBarAgent(height, width / 2, layoutPres);
				
		this.getChildren().addAll(layoutView, sideBar);
	}	

}
