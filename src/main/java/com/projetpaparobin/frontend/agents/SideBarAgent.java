package com.projetpaparobin.frontend.agents;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.output.BigBoiFinalFileGenerator;
import com.projetpaparobin.frontend.agents.inputs.ETypeAction;
import com.projetpaparobin.frontend.agents.inputs.FileGenerationDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.utils.UIElements;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideBarAgent extends VBox implements EventHandler<ActionEvent> {

	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static BigBoiFinalFileGenerator fileGenerator = BigBoiFinalFileGenerator.getInstance();
	
	private FileGenerationDialogHandler fileGenerationInputDialog;
	private Button newExtinguisherButton, newZoneButton, doneEditingZoneButton, createExcelButton, cancelButton, resetButton;
	private PresentationLayoutAgent presLayoutAgent;
	private UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	public SideBarAgent(int height, int width, PresentationLayoutAgent presLayoutAgent) {
		super(50);
		this.setPadding(new Insets(15, 0, 0, 0));
		this.setBorder(UIElements.BLACK_BORDER);
		this.setPrefSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
		this.presLayoutAgent = presLayoutAgent;
		this.fileGenerationInputDialog = new FileGenerationDialogHandler();

		newExtinguisherButton = new Button("Nouveau extincteur");
		newExtinguisherButton.addEventHandler(ActionEvent.ACTION, this);
		newZoneButton = new Button("Nouvelle zone");
		newZoneButton.addEventHandler(ActionEvent.ACTION, this);
		doneEditingZoneButton = new Button("Fin création de zone");
		doneEditingZoneButton.addEventHandler(ActionEvent.ACTION, this);
		createExcelButton = new Button("Générer fichier Excel");
		createExcelButton.addEventHandler(ActionEvent.ACTION, this);
		cancelButton = new Button("Annuler");
		cancelButton.addEventHandler(ActionEvent.ACTION, this);
		resetButton = new Button("Reset");
		resetButton.addEventHandler(ActionEvent.ACTION, this);
		
		UIElements.setDefaultButtonStyle(newExtinguisherButton);
		UIElements.setDefaultButtonStyle(newZoneButton);
		UIElements.setDefaultButtonStyle(doneEditingZoneButton);
		UIElements.setDefaultButtonStyle(createExcelButton);
		UIElements.setDefaultButtonStyle(cancelButton);
		UIElements.setDefaultButtonStyle(resetButton);
		
		VBox zoneBox = new VBox(8, newZoneButton, doneEditingZoneButton);
		zoneBox.setAlignment(Pos.CENTER);
		VBox exBox = new VBox(8, newExtinguisherButton, cancelButton);
		exBox.setAlignment(Pos.CENTER);
		
		HBox finalBox = new HBox(8, zoneBox, exBox, createExcelButton);
		finalBox.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(finalBox, resetButton);
	}

	public void setPresLayoutAgent(PresentationLayoutAgent presLayoutAgent) {
		this.presLayoutAgent = presLayoutAgent;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(newExtinguisherButton)) {
			presLayoutAgent.updateCanvas();
			extinguisherCreator.newExtinguisher();
		} else if(event.getSource().equals(newZoneButton)) {
			presLayoutAgent.updateCanvas();
			zoneCreator.newZone();
		} else if(event.getSource().equals(doneEditingZoneButton)) {
			zoneCreator.finishedCreatingShape();
		} else if(event.getSource().equals(createExcelButton)) {
			String response = fileGenerationInputDialog.showAndWait();
			if(!response.isBlank()) {
				SnapshotParameters sp = new SnapshotParameters();
			    sp.setFill(Color.TRANSPARENT);
			    File file = new File("./" + response + ".png");
		        WritableImage wi = presLayoutAgent.getSnapshot(sp, null);
			    try {
					ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fileGenerator.generateExcel(response + ".xlsm");
			}
		} else if(event.getSource().equals(cancelButton)) {
			zoneHandler.removeSelectedZone();
			zoneCreator.canceled();
		} else if(event.getSource().equals(resetButton)) {
			layoutHandler.fullReset();
			zoneCreator.canceled();			
		}
	}
	
}
