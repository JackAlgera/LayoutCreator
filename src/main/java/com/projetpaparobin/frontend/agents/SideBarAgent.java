package com.projetpaparobin.frontend.agents;

import com.projetpaparobin.documents.output.BigBoiFinalFileGenerator;
import com.projetpaparobin.frontend.agents.inputs.ETypeAction;
import com.projetpaparobin.frontend.agents.inputs.FileGenerationDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
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
	private static BigBoiFinalFileGenerator fileGenerator = BigBoiFinalFileGenerator.getInstance();
	
	private FileGenerationDialogHandler fileGenerationInputDialog;
	private Button newExtinguisherButton, newShapeButton, doneEditingShapeButton, createExcelButton, cancelButton;
	private PresentationLayoutAgent presLayoutAgent;
	private MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	private UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	
	public SideBarAgent(int height, int width, PresentationLayoutAgent presLayoutAgent) {
		super();
		this.setBorder(UIElements.BLACK_BORDER);
		this.setPrefSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
		this.presLayoutAgent = presLayoutAgent;
		this.fileGenerationInputDialog = new FileGenerationDialogHandler();

		newExtinguisherButton = new Button("Nouveau extincteur");
		newExtinguisherButton.addEventHandler(ActionEvent.ACTION, this);
		newShapeButton = new Button("Nouvelle zone");
		newShapeButton.addEventHandler(ActionEvent.ACTION, this);
		doneEditingShapeButton = new Button("Fin création de zone");
		doneEditingShapeButton.addEventHandler(ActionEvent.ACTION, this);
		createExcelButton = new Button("Générer fichier Excel");
		createExcelButton.addEventHandler(ActionEvent.ACTION, this);
		cancelButton = new Button("Annuler");
		cancelButton.addEventHandler(ActionEvent.ACTION, this);
		
		this.getChildren().addAll(newExtinguisherButton, newShapeButton, doneEditingShapeButton, cancelButton, createExcelButton);
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
		} else if(event.getSource().equals(createExcelButton)) {
			String response = fileGenerationInputDialog.showAndWait();
			if(!response.isBlank()) {
				fileGenerator.generateExcel(response + ".xlsm");
			}
		} else if(event.getSource().equals(cancelButton)) {
			zoneHandler.removeSelectedZone();
			presLayoutAgent.updateCanvas();
			mouseInputHandler.setState(ETypeAction.IDLE);
		}
	}
	
}
