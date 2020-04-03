package com.projetpaparobin.frontend.agents.inputs;

import java.util.Optional;

import com.projetpaparobin.utils.UIElements;
import com.projetpaparobin.zones.Identifiant;
import com.projetpaparobin.zones.creators.EZoneEvents;
import com.projetpaparobin.zones.creators.IZoneCreatorListener;
import com.projetpaparobin.zones.creators.ZoneCreator;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

public class InputDialogHandler implements IZoneCreatorListener {

	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private Dialog<Identifiant> inputDialog;
	
	public InputDialogHandler() {
		inputDialog = new Dialog<Identifiant>();
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		TextField areaTypeTextField  = new TextField();
		areaTypeTextField.setPromptText("Area Type");
		areaTypeTextField.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		TextField areaNumberTextField = new TextField();
		areaNumberTextField.setPromptText("Area number");
		areaNumberTextField.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		TextField activityTypeTextField  = new TextField();
		activityTypeTextField.setPromptText("Activity Type");
		activityTypeTextField.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		TextField areaSizesTextField  = new TextField();
		areaSizesTextField.setPromptText("Area size");
		areaSizesTextField.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
				
		dialogPane.setContent(new VBox(8, areaTypeTextField, areaNumberTextField, activityTypeTextField, areaSizesTextField));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String areaType = areaTypeTextField.getText();
				String areaNumber = areaNumberTextField.getText();
				String activityType = activityTypeTextField.getText();
				String areaSize = areaSizesTextField.getText();
				
				areaTypeTextField.setText("");
				areaNumberTextField.setText("");
				activityTypeTextField.setText("");
				areaSizesTextField.setText("");
				
				if(areaSize.isBlank()) {
					areaSize = "-1";
				}
				if(areaNumber.isBlank()) {
					areaNumber = "-1";
				}
				
				return new Identifiant(areaType, Integer.parseInt(areaNumber), activityType, Integer.parseInt(areaSize));
			} 
			if(button == ButtonType.CANCEL) {
				zoneCreator.canceled();
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(false);
		zoneCreator.addListener(this);
	}

	@Override
	public void handleZoneCreatorEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			break;
		case ADDED_POINT:
			break;
		case SETTING_NAME:
			Optional<Identifiant> response = inputDialog.showAndWait();
			if(!response.isEmpty()) {
				zoneCreator.setZoneIdentifiant(response.get().getAreaType(), 
						response.get().getAreaNumber(),
						response.get().getActivityType(), 
						response.get().getAreaSize());
			}
			break;
		case FINISHED_CREATING_ZONE:
			break;
		case CANCELED:
			break;
		}
	}
	
}
