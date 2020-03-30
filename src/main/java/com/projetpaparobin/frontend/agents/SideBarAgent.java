package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.utils.UIElements;

import javafx.scene.layout.VBox;

public class SideBarAgent extends VBox {
	
	public SideBarAgent(int height, int width) {
		super();
		this.setBorder(UIElements.BLACK_BORDER);
		this.setPrefSize(width, height);
		
	}
	
}
