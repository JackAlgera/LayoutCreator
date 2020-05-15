package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.frontend.agents.menubar.UIMenuBar;
import com.projetpaparobin.frontend.agents.recapagent.SideBarRecapAgent;
import com.projetpaparobin.utils.UIElements;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainUI extends VBox {

	private static double widthRatio = 0.6;
	private static double heightRatio = 0.35;
	
	private UIMenuBar menuBar;
	private UITabHandler uiTabBar;
	
	private PresentationLayoutAgent layoutPres;
	private ViewLayoutAgent layoutView;
	private SideBarButtonAgent sideBarButtonAgent;
	private SideBarRecapAgent sideBarRecapAgent;
	
	private VBox sideBox;

	public MainUI() {
	}
	
	public MainUI(Stage primaryStage, double width, double height) {
		super();	
		menuBar = new UIMenuBar(primaryStage);
		uiTabBar = new UITabHandler();
		
		layoutPres = new PresentationLayoutAgent();
		layoutView = new ViewLayoutAgent(primaryStage, width * widthRatio, height, layoutPres);
		layoutPres.setView(layoutView);		

		sideBarButtonAgent = new SideBarButtonAgent(layoutPres, primaryStage, width * (1.0 - widthRatio), height * heightRatio);				
		sideBarRecapAgent = new SideBarRecapAgent(layoutPres, width * (1.0 - widthRatio), height * (1.0 - heightRatio));
		
		sideBox = new VBox(8, sideBarButtonAgent, sideBarRecapAgent);
		sideBox.setAlignment(Pos.CENTER);
		sideBox.setMaxSize(width * (1.0 - widthRatio), height);
		sideBox.setMinSize(width * (1.0 - widthRatio), height);
		sideBox.setBorder(UIElements.BLACK_BORDER);
		
		this.getChildren().addAll(menuBar, uiTabBar, new HBox(layoutView, sideBox));
	}	

	public void resizePanel(double width, double height) {
		height -= 27 + menuBar.getHeight();
		sideBox.setMaxSize(width * (1.0 - widthRatio), height);
		sideBox.setMinSize(width * (1.0 - widthRatio), height);
		
		layoutView.resizePanel((int) (width * widthRatio), height);
		sideBarButtonAgent.resizePanel(width * (1.0 - widthRatio), height * heightRatio);
		sideBarRecapAgent.resizePanel(width * (1.0 - widthRatio), height * (1.0 - heightRatio));
		layoutPres.updateShapes();
	}
	
}
