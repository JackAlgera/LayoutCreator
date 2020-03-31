package com.projetpaparobin.frontend.agents;

import java.util.ArrayList;
import java.util.Arrays;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.frontend.shapes.Circle;
import com.projetpaparobin.frontend.shapes.UIShape;
import com.projetpaparobin.utils.UIElements;
import com.projetpaparobin.zones.Point;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MainUI extends HBox {

	private PresentationLayoutAgent layoutPres;
	private ViewLayoutAgent layoutView;
	private SideBarAgent sideBar;
	
	private LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	public MainUI(int height, int width, String layoutPath) {
		super();
		this.setPrefSize(width, height);	
		
		sideBar = new SideBarAgent(height, width / 2);
		layoutPres = new PresentationLayoutAgent();
		layoutView = new ViewLayoutAgent(layoutPath, 1, height, width / 2, layoutPres);
		layoutPres.setView(layoutView);
				
		this.getChildren().addAll(layoutView, sideBar);
	}	

}
