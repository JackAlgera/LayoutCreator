package com.projetpaparobin.frontend.agents.recapagent;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.recapagent.tableviews.ExtinguishersTableView;
import com.projetpaparobin.frontend.agents.recapagent.tableviews.ZoneTableView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SideBarRecapAgent extends VBox {

	private ZoneTableView zoneTableView;
	private ExtinguishersTableView extinguisherTableView;	
	
	public SideBarRecapAgent(PresentationLayoutAgent presLayout, double width, double height) {
		super();
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setAlignment(Pos.CENTER);
		
		zoneTableView = new ZoneTableView(presLayout, width);
		extinguisherTableView = new ExtinguishersTableView(presLayout, width);
		
		this.getChildren().addAll(zoneTableView, extinguisherTableView);
	}
			
	public void resizePanel(double width, double height) {
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		zoneTableView.resizePanel(width, height);
		extinguisherTableView.resizePanel(width, height);
	}
	
}
