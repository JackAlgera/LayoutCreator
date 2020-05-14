package com.projetpaparobin.frontend.agents;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.output.BigBoiFinalFileGenerator;
import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.FileGenerationDialogHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.utils.UIElements;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private static TabHandler tabHandler = TabHandler.getInstance();
	
	private FileGenerationDialogHandler fileGenerationInputDialog;
	
	private Button newExtinguisherButton, newZoneButton, doneEditingZoneButton, createExcelButton, cancelButton, createCommentButton, refreshButton;
	private CheckBox setShouldDrawNewExCheckBox, setShouldDrawOldExCheckBox, setShouldDrawZonesCheckBox, setShouldDrawCommentsCheckBox;
	private PresentationLayoutAgent presLayoutAgent;
	private MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	
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
		doneEditingZoneButton = new Button("Fin création de zone");
		doneEditingZoneButton.addEventHandler(ActionEvent.ACTION, this);
		createExcelButton = new Button("Générer fichier Excel et PNG");
		createExcelButton.addEventHandler(ActionEvent.ACTION, this);
		cancelButton = new Button("Annuler");
		cancelButton.addEventHandler(ActionEvent.ACTION, this);
		createCommentButton = new Button("Nouveau commentaire");
		createCommentButton.addEventHandler(ActionEvent.ACTION, this);
		refreshButton = new Button("Rafraîchir");
		refreshButton.addEventHandler(ActionEvent.ACTION, this);
		
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
		
		setShouldDrawNewExCheckBox = new CheckBox("Should Draw New Ex");
		setShouldDrawNewExCheckBox.setSelected(true);
		setShouldDrawNewExCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			presLayoutAgent.setShouldDrawNewEx(newValue);
		});
		setShouldDrawOldExCheckBox = new CheckBox("Should Draw Old Ex");
		setShouldDrawOldExCheckBox.setSelected(true);
		setShouldDrawOldExCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			presLayoutAgent.setShouldDrawOldEx(newValue);
		});
		setShouldDrawZonesCheckBox = new CheckBox("Should Draw Zones");
		setShouldDrawZonesCheckBox.setSelected(true);
		setShouldDrawZonesCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			presLayoutAgent.setShouldDrawZones(newValue);
		});
		setShouldDrawCommentsCheckBox = new CheckBox("Should Draw Comments");
		setShouldDrawCommentsCheckBox.setSelected(true);
		setShouldDrawCommentsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			presLayoutAgent.setShouldDrawComments(newValue);
		});	
		
		HBox finalBox = new HBox(8, zoneBox, exBox, new VBox(8, createExcelButton, refreshButton));
		finalBox.setAlignment(Pos.CENTER);
				
		this.getChildren().addAll(finalBox, new VBox(8, setShouldDrawNewExCheckBox, setShouldDrawOldExCheckBox, setShouldDrawZonesCheckBox, setShouldDrawCommentsCheckBox));
	}

	public void setPresLayoutAgent(PresentationLayoutAgent presLayoutAgent) {
		this.presLayoutAgent = presLayoutAgent;
	}
	
	@Override
	public void handle(ActionEvent event) {
		LayoutHandler layoutHandler = tabHandler.getSelectedLayoutHandler();
		if(layoutHandler == null) {
			return;
		}
		
		if(event.getSource().equals(newExtinguisherButton)) {
			presLayoutAgent.updateCanvas();
			layoutHandler.getExtinguisherCreator().newExtinguisher();
		} else if(event.getSource().equals(newZoneButton)) {
			presLayoutAgent.updateCanvas();
			layoutHandler.getZoneCreator().newZone();
			doneEditingZoneButton.requestFocus();
		} else if(event.getSource().equals(createCommentButton)) {
			layoutHandler.getCommentCreator().newComment();
		} else if(event.getSource().equals(doneEditingZoneButton)) {
			layoutHandler.getZoneCreator().finishedCreatingShape();			
		} else if(event.getSource().equals(createExcelButton)) {
			String response = fileGenerationInputDialog.showAndWait();
			if(!response.isBlank()) {
			    String filePath = (dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH).isBlank() ? "." : dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH)) + "/" + response;
			    saveAllLayouts(filePath);
				fileGenerator.generateExcel(filePath + ".xlsm");
			}
		} else if(event.getSource().equals(cancelButton)) {
			mouseInputHandler.cancelSelection();
			layoutHandler.getZoneCreator().canceled();
			layoutHandler.getCommentCreator().canceled();
			layoutHandler.getExtinguisherCreator().canceled();
		} else if(event.getSource().equals(refreshButton)) {
			presLayoutAgent.updateShapes();
		}
	}
	
	public void saveAllLayouts(String filePath) {
		SnapshotParameters sp = new SnapshotParameters();
	    sp.setFill(Color.TRANSPARENT);	
	        
	    try {	
	    	for (int i = 0; i < tabHandler.getLayoutHandlers().size(); i++) {
				tabHandler.selectLayoutHandler(i);
				LayoutHandler layoutHandler = tabHandler.getLayoutHandlers().get(i);
				saveOneLayout(filePath, layoutHandler, sp);
			}
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    resetCheckBoxes();
	}
	
	private void saveOneLayout(String filePath, LayoutHandler layoutHandler, SnapshotParameters sp) throws IOException {
	    presLayoutAgent.showLayoutWithoutZones();
	    WritableImage wi = presLayoutAgent.getSnapshot(sp, null);
		ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", new File(filePath + "_" + layoutHandler.getLayoutName() + "_plan_sans_zones" + ".png"));
		
	    presLayoutAgent.showLayoutWithoutZonesAndOldExtinguishers();
        wi = presLayoutAgent.getSnapshot(sp, null);
		ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", new File(filePath + "_" + layoutHandler.getLayoutName() + "_extincteurs_nouv_uniquement" + ".png"));
		
	    presLayoutAgent.setShouldDrawEverything(true);
        wi = presLayoutAgent.getSnapshot(sp, null);
		ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", new File(filePath + "_" + layoutHandler.getLayoutName() + "_plan_complet" + ".png"));
	}
	
	public void resetCheckBoxes() {
		setShouldDrawNewExCheckBox.setSelected(true);
		setShouldDrawOldExCheckBox.setSelected(true);
		setShouldDrawZonesCheckBox.setSelected(true);
		setShouldDrawCommentsCheckBox.setSelected(true);
	}
	
	public void resizePanel(double width, double height) {
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
	}
	
}
