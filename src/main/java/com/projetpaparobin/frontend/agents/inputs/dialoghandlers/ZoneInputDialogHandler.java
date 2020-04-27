package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.extinguishers.EProtectionType;
import com.projetpaparobin.objects.zones.EActivityType;
import com.projetpaparobin.objects.zones.EAreaType;
import com.projetpaparobin.objects.zones.EUnits;
import com.projetpaparobin.objects.zones.ZoneID;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class ZoneInputDialogHandler extends DialogHandlerAbs implements IZoneCreatorListener {

	private static double DEFAULT_SPACE_BETWEEN_INPUTS = 8;
	private static double width = 180;
	
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private Dialog<ZoneID> inputDialog;

	private double initHeight;
	
	public ZoneInputDialogHandler(Window primaryStage) {
		super(primaryStage);
		zoneCreator.addListener(this);
		inputDialog = new Dialog<ZoneID>();
		inputDialog.setTitle("Nouvelle zone");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefWidth(width);
		
		TextField areaName = new TextField();
		areaName.setPromptText("Area name");
			
		TextField areaNumber = new TextField();
		areaNumber.setPromptText("Area number");
		areaNumber.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));

		ComboBox<String> activityType = new ComboBox<String>(FXCollections.observableArrayList(Stream.of(EActivityType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList())));	
		activityType.setValue(EActivityType.values()[0].toString());
		activityType.setPrefWidth(width);
		
		ComboBox<String> units = new ComboBox<String>(FXCollections.observableArrayList(FXCollections.observableArrayList(Stream.of(EUnits.values())
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		units.setValue(EUnits.values()[0].toString());
		units.setPrefWidth(width);
		
		TextField areaSize = new TextField();
		areaSize.setPromptText("Area size");
		areaSize.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
				
		ComboBox<String> areaType = new ComboBox<String>(FXCollections.observableArrayList(Stream.of(EAreaType.values())
						.map(val -> val.toString())
						.collect(Collectors.toList())));	
		areaType.setValue(EAreaType.values()[0].toString());
		areaType.setPrefWidth(width);
		areaType.valueProperty().addListener((observable, oldValue, newValue) -> {
			EAreaType type = EAreaType.getEnum(newValue);
			if(type.equals(EAreaType.ZIP)) {
				dialogPane.setContent(new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, areaName, areaType, units, areaNumber, activityType, areaSize));
				inputDialog.setHeight(inputDialog.getHeight() + areaName.getHeight() + DEFAULT_SPACE_BETWEEN_INPUTS);
			} else {
				dialogPane.setContent(new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, areaName, areaType, areaNumber, activityType, areaSize));
				inputDialog.setHeight(initHeight);
			}
		});
		
		VBox vbox = new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, areaName, areaType, areaNumber, activityType, areaSize);
		vbox.setFillWidth(true);
		
		dialogPane.setContent(vbox);
		initHeight = inputDialog.getHeight();
		
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String areaNameVal = areaName.getText();
				EAreaType areaTypeVal = EAreaType.valueOf(areaType.getValue());
				int areaNumberVal = (areaNumber.getText().isBlank()) ? ZoneCreator.getDefaultZoneNumber() : Integer.parseInt(areaNumber.getText());
				EActivityType activityTypeVal = EActivityType.getEnum(activityType.getValue());
				int areaSizeVal = (areaSize.getText().isBlank()) ? 0 : Integer.parseInt(areaSize.getText());
				EUnits unitsVal = (units == null) ? EUnits.m2 : EUnits.getEnum(units.getValue());
				
				areaName.setText("");
				areaType.setValue(EAreaType.values()[0].toString());
				areaNumber.setText("");
				activityType.setValue(EActivityType.values()[0].toString());
				areaSize.setText("");
				units.setValue("Kg");
				
				return new ZoneID(areaNameVal, areaTypeVal, areaNumberVal, activityTypeVal, areaSizeVal, unitsVal);
			} 
			if(button == ButtonType.CANCEL) {
				zoneCreator.canceled();
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(false);
	}

	@Override
	public void handleZoneCreatorEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			break;
		case ADDED_POINT:
			break;
		case SETTING_NAME:
			if(inputDialog.getOwner() == null) {
				inputDialog.initOwner(primaryStage);
			}
			Optional<ZoneID> response = inputDialog.showAndWait();
			if(!response.isEmpty()) {
				zoneCreator.setZoneID(response.get());
			}
			break;
		case FINISHED_CREATING_ZONE:
			break;
		case CANCELED:
			break;
		}
	}
	
}
