package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.frontend.agents.recapagent.SideBarRecapAgent;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainUI extends HBox {

	private static double widthRatio = 0.6;
	private static double heightRatio = 0.35;
	
	private PresentationLayoutAgent layoutPres;
	private ViewLayoutAgent layoutView;
	private SideBarButtonAgent sideBarButtonAgent;
	private SideBarRecapAgent sideBarRecapAgent;

	public MainUI(Stage primaryStage, int height, int width, String excelTemplatePath, String layoutPDFPath, int layoutPageNum) {
		super();
		this.setPrefSize(width, height);	
		
		layoutPres = new PresentationLayoutAgent();
		layoutView = new ViewLayoutAgent(layoutPDFPath, layoutPageNum, height, (int) (width * widthRatio), layoutPres);
		layoutPres.setView(layoutView);		

		sideBarButtonAgent = new SideBarButtonAgent(primaryStage, (int) (height * heightRatio), (int) (width * (1.0 - widthRatio)) , layoutPres);				
		sideBarRecapAgent = new SideBarRecapAgent((int) (height * (1.0 - heightRatio)), (int) (width * (1.0 - widthRatio)));
		
		VBox sideBox = new VBox(8, sideBarButtonAgent, sideBarRecapAgent);
		sideBox.setBorder(UIElements.BLACK_BORDER);
		
		this.getChildren().addAll(layoutView, sideBox);
	}	

}
