package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.utils.UIElements;
import com.projetpaparobin.zones.creators.IZoneCreatorListener;
import com.projetpaparobin.zones.creators.ZoneCreator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SideBarAgent extends VBox implements EventHandler<ActionEvent> {

	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private Button newShapeButton;
	
	public SideBarAgent(int height, int width) {
		super();
		this.setBorder(UIElements.BLACK_BORDER);
		this.setPrefSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
				
		newShapeButton = new Button("New shape");
		newShapeButton.addEventHandler(ActionEvent.ACTION, this);
		
		this.getChildren().addAll(newShapeButton);
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(newShapeButton)) {
			zoneCreator.newZone();
		}
	}
	
}
