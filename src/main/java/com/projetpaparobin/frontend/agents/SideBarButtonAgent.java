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
import com.projetpaparobin.objects.creators.comments.CommentCreator;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SideBarButtonAgent extends VBox implements EventHandler<ActionEvent> {

	private static BigBoiFinalFileGenerator fileGenerator = BigBoiFinalFileGenerator.getInstance();
	
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static CommentCreator commentCreator = CommentCreator.getInstance();
	
	private FileGenerationDialogHandler fileGenerationInputDialog;
	
	private Button newExtinguisherButton, newZoneButton, doneEditingZoneButton, createExcelButton, cancelButton, createCommentButton;
	private PresentationLayoutAgent presLayoutAgent;
	private MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	private LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	public SideBarButtonAgent(PresentationLayoutAgent presLayoutAgent, Stage primaryStage, double width, double height) {
		super(50);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		this.setAlignment(Pos.TOP_CENTER);
		this.setBorder(new Border(new BorderStroke(
				Color.GREEN, 
				BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, 
				BorderWidths.DEFAULT)));
		this.presLayoutAgent = presLayoutAgent;
		this.fileGenerationInputDialog = new FileGenerationDialogHandler(primaryStage);
		
		newExtinguisherButton = new Button("Nouveau extincteur");
		newExtinguisherButton.addEventHandler(ActionEvent.ACTION, this);
		newZoneButton = new Button("Nouvelle zone");
		newZoneButton.addEventHandler(ActionEvent.ACTION, this);
		doneEditingZoneButton = new Button("Fin cr�ation de zone");
		doneEditingZoneButton.addEventHandler(ActionEvent.ACTION, this);
		createExcelButton = new Button("G�n�rer fichier Excel et PNG");
		createExcelButton.addEventHandler(ActionEvent.ACTION, this);
		cancelButton = new Button("Annuler");
		cancelButton.addEventHandler(ActionEvent.ACTION, this);
		createCommentButton = new Button("Nouveau commentaire");
		createCommentButton.addEventHandler(ActionEvent.ACTION, this);
		
		UIElements.setDefaultButtonStyle(newExtinguisherButton);
		UIElements.setDefaultButtonStyle(newZoneButton);
		UIElements.setDefaultButtonStyle(doneEditingZoneButton);
		UIElements.setDefaultButtonStyle(createExcelButton);
		UIElements.setDefaultButtonStyle(cancelButton);
		UIElements.setDefaultButtonStyle(createCommentButton);
		
		VBox zoneBox = new VBox(8, newZoneButton, doneEditingZoneButton);
		zoneBox.setAlignment(Pos.CENTER);
		VBox exBox = new VBox(8, newExtinguisherButton, createCommentButton, cancelButton);
		exBox.setAlignment(Pos.CENTER);
		
		HBox finalBox = new HBox(8, zoneBox, exBox, createExcelButton);
		finalBox.setAlignment(Pos.CENTER);
				
		this.getChildren().addAll(finalBox);
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
		} else if(event.getSource().equals(createCommentButton)) {
			commentCreator.newComment();
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
		} 
	}
	
	public void resizePanel(double width, double height) {
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
	}
	
}
