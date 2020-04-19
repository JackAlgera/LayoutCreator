package com.projetpaparobin.frontend.agents.recapagent;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SideBarRecapAgent extends VBox {

	private ZoneTableView zoneTableView;
	private ExtinguishersTableView extinguisherTableView;	
	
	public SideBarRecapAgent(PresentationLayoutAgent presLayout, int height, int width) {
		super();
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setPrefSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
		
		zoneTableView = new ZoneTableView(presLayout, width);
		extinguisherTableView = new ExtinguishersTableView(width);
		
		this.getChildren().addAll(zoneTableView, extinguisherTableView);
	}
		
}
