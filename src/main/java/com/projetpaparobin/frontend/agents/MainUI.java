package com.projetpaparobin.frontend.agents;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MainUI extends HBox implements EventHandler<MouseEvent> {

	private LayoutAgent layout;
	private SideBarAgent sideBar;
	
	public MainUI(int height, int width, String layoutPath) {
		super();
		this.setPrefSize(width, height);	
		
		sideBar = new SideBarAgent(height, width / 2);
		layout = new LayoutAgent(layoutPath, 1, height, width / 2, this);
	
		this.getChildren().addAll(layout, sideBar);
	}	

	@Override
	public void handle(MouseEvent event) {
		System.out.println("Button:" + event.getButton() + " x=" + event.getX() + " y=" + event.getY());
	}
}
