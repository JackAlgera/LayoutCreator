package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.EActivityType;
import com.projetpaparobin.objects.zones.EAreaType;
import com.projetpaparobin.objects.zones.EUnits;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.objects.zones.ZoneID;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class ZoneInputDialogHandler extends DialogHandlerAbs implements ITabHandler, IZoneCreatorListener {

	private static double DEFAULT_SPACE_BETWEEN_INPUTS = 8;
	private static double width = 180;

	private static TabHandler tabHandler = TabHandler.getInstance();
	private static ZoneCreator zoneCreator = null;
	
	private Dialog<ZoneID> inputDialog;
	private ComboBox<UIColor> colorComboBox;
	private ComboBox<String> activityType, units, areaType;
	private TextField areaNumber, areaName, areaSize;
		
	public ZoneInputDialogHandler(Window primaryStage, PresentationLayoutAgent presLayout) {
		super(primaryStage);
		inputDialog = new Dialog<ZoneID>();
		inputDialog.setTitle("Nouvelle zone");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefWidth(width);
		
		areaName = new TextField();
		areaName.setPromptText("Nom de la zone");
			
		areaNumber = new TextField();
		areaNumber.setPromptText("Numérotation de la zone");
		areaNumber.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));

		activityType = new ComboBox<String>(FXCollections.observableArrayList(Stream.of(EActivityType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList())));	
		activityType.setValue(EActivityType.values()[0].toString());
		activityType.setPrefWidth(width);
		
		units = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(Stream.of(EUnits.values())
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		units.setValue(EUnits.values()[0].toString());
		units.setPrefWidth(width);
		
		areaSize = new TextField();
		areaSize.setPromptText("Taille de la zone");
		areaSize.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
				
		colorComboBox = new ComboBox<UIColor>(FXCollections.observableArrayList(UIElements.DEFAULT_ZONE_COLORS));
		colorComboBox.setPrefWidth(width);	
		colorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
				if(zoneCreator == null) {
					return;
				}
				Zone zone = zoneCreator.getCurrentZone();
				if(zone != null && !zone.getShape().getPoints().isEmpty()) {
					zone.setFillColor(colorComboBox.getValue());
					presLayout.handleZoneCreatorEvent(EZoneEvents.ADDED_POINT);
				}
		});
		
		areaType = new ComboBox<String>(FXCollections.observableArrayList(Stream.of(EAreaType.values())
						.map(val -> val.toString())
						.collect(Collectors.toList())));	
		areaType.setValue(EAreaType.values()[0].toString());
		areaType.setPrefWidth(width);
		areaType.valueProperty().addListener((observable, oldValue, newValue) -> {
			EAreaType type = EAreaType.getEnum(newValue);
			if(type.equals(EAreaType.ZIP)) {
				dialogPane.setContent(new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, areaName, areaType, units, areaNumber, activityType, areaSize, colorComboBox));
				dialogPane.getScene().getWindow().sizeToScene();
				activityType.setValue(EActivityType.INDUSTRIELLE.toString());
			} else {
				dialogPane.setContent(new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, areaName, areaType, areaNumber, activityType, areaSize, colorComboBox));
				dialogPane.getScene().getWindow().sizeToScene();
			}
		});	
		
		VBox vbox = new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, areaName, areaType, areaNumber, activityType, areaSize, colorComboBox);
		vbox.setFillWidth(true);

		dialogPane.setContent(vbox);
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);
		
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String areaNameVal = areaName.getText();
				EAreaType areaTypeVal = EAreaType.valueOf(areaType.getValue());
				int areaNumberVal = (areaNumber.getText().isBlank()) ? (zoneCreator == null ? -1 : zoneCreator.getDefaultZoneNumber()) : Integer.parseInt(areaNumber.getText());
				EActivityType activityTypeVal = EActivityType.getEnum(activityType.getValue());
				int areaSizeVal = (areaSize.getText().isBlank()) ? 0 : Integer.parseInt(areaSize.getText());
				EUnits unitsVal = (units == null) ? EUnits.m2 : EUnits.getEnum(units.getValue());
				UIColor colorVal = colorComboBox.getValue();
				
				resetFields();
				
				return new ZoneID(areaNameVal, areaTypeVal, areaNumberVal, activityTypeVal, areaSizeVal, unitsVal, colorVal);
			} 
			if(button == ButtonType.CANCEL) {
				resetFields();
				if(zoneCreator != null) {
					zoneCreator.canceled();
				}
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(false);
		tabHandler.addListener(this);
	}

	@Override
	public void handleZoneCreatorEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			colorComboBox.setValue(zoneCreator.getCurrentZone().getFillColor());
			break;
		case ADDED_POINT:
			break;
		case SETTING_NAME:
			if(inputDialog.getOwner() == null) {
				inputDialog.initOwner(primaryStage);
			}
			Optional<ZoneID> response = inputDialog.showAndWait();
			if(!response.isEmpty() && zoneCreator != null) {
				zoneCreator.setZoneID(response.get());
			}
			break;
		case FINISHED_CREATING_ZONE:
			break;
		case CANCELED:
			break;
		}
	}

	@Override
	public void handleTabHandlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
			break;
		case CHANGED_SELECTED_TAB:
			if(zoneCreator != null) {
				zoneCreator.removeListener(this);
			}
			
			zoneCreator = tabHandler.getSelectedLayoutHandler().getZoneCreator();

			if(zoneCreator != null) {
				zoneCreator.addListener(this);
			}
			break;
		case REMOVED_TAB:
			break;
		case FULL_RESET:
			break;
		}
	}
	
	private void resetFields() {
		areaName.setText("");
		areaType.setValue(EAreaType.values()[0].toString());
		areaNumber.setText("");
		activityType.setValue(EActivityType.values()[0].toString());
		areaSize.setText("");
		units.setValue("kg");
	}
	
}
