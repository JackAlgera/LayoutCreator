package com.projetpaparobin.frontend.agents;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.output.BigBoiFinalFileGenerator;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.AreYouSureInputDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.FileGenerationDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.FileSaveInputDialogHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
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
import javafx.stage.Stage;

public class SideBarButtonAgent extends VBox implements EventHandler<ActionEvent> {

	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static BigBoiFinalFileGenerator fileGenerator = BigBoiFinalFileGenerator.getInstance();
	
	private FileGenerationDialogHandler fileGenerationInputDialog;
	private AreYouSureInputDialogHandler areYouSureInputDialog;
	private FileSaveInputDialogHandler fileSaveInputDialog;
	
	private Button newExtinguisherButton, newZoneButton, doneEditingZoneButton, createExcelButton, cancelButton, resetButton, saveButton, loadButton;
	private PresentationLayoutAgent presLayoutAgent;
	private MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	private LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	public SideBarButtonAgent(Stage primaryStage, int height, int width, PresentationLayoutAgent presLayoutAgent) {
		super(50);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setPrefSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
		this.presLayoutAgent = presLayoutAgent;
		this.fileGenerationInputDialog = new FileGenerationDialogHandler();
		this.areYouSureInputDialog = new AreYouSureInputDialogHandler("Confirmation De Suppression", "Etes-vous sûr de vouloir tout supprimer ?");
		this.fileSaveInputDialog = new FileSaveInputDialogHandler(primaryStage);
		
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
		saveButton = new Button("Save");
		saveButton.addEventHandler(ActionEvent.ACTION, this);
		loadButton = new Button("Load");
		loadButton.addEventHandler(ActionEvent.ACTION, this);
		
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
		
		HBox stateButtons = new HBox(8, saveButton, loadButton);
		stateButtons.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(finalBox, stateButtons, resetButton);
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
			mouseInputHandler.cancelSelection();
			zoneCreator.canceled();
		} else if(event.getSource().equals(resetButton)) {
			if(areYouSureInputDialog.showAndWait()) {
				layoutHandler.fullReset();
				zoneCreator.canceled();			
			}
		} else if(event.getSource().equals(saveButton)) {
			fileSaveInputDialog.showSaveDialog();
		} else if(event.getSource().equals(loadButton)) {
			fileSaveInputDialog.showLoadDialog();
		}
	}
	
}
