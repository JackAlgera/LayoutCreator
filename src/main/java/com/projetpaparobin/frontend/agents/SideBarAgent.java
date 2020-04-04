package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.utils.UIElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SideBarAgent extends VBox implements EventHandler<ActionEvent> {

	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	
	private Button newExtinguisherButton, newShapeButton, doneEditingShapeButton;
	private PresentationLayoutAgent presLayoutAgent;
	
	public SideBarAgent(int height, int width, PresentationLayoutAgent presLayoutAgent) {
		super();
		this.setBorder(UIElements.BLACK_BORDER);
		this.setPrefSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
		this.presLayoutAgent = presLayoutAgent;

		newExtinguisherButton = new Button("New extinguisher");
		newExtinguisherButton.addEventHandler(ActionEvent.ACTION, this);
		newShapeButton = new Button("New zone");
		newShapeButton.addEventHandler(ActionEvent.ACTION, this);
		doneEditingShapeButton = new Button("Finished editing shape");
		doneEditingShapeButton.addEventHandler(ActionEvent.ACTION, this);
		
		this.getChildren().addAll(newExtinguisherButton, newShapeButton, doneEditingShapeButton);
	}

	public void setPresLayoutAgent(PresentationLayoutAgent presLayoutAgent) {
		this.presLayoutAgent = presLayoutAgent;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(newExtinguisherButton)) {
			presLayoutAgent.updateCanvas();
			extinguisherCreator.newExtinguisher();
		} else if(event.getSource().equals(newShapeButton)) {
			presLayoutAgent.updateCanvas();
			zoneCreator.newZone();
		} else if(event.getSource().equals(doneEditingShapeButton)) {
			zoneCreator.finishedCreatingShape();
		}
	}
	
}
