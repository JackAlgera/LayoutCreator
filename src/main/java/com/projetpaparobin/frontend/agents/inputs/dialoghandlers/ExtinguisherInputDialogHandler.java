package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.controlsfx.control.textfield.TextFields;

import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.extinguishers.EExtinguisherType;
import com.projetpaparobin.objects.extinguishers.EProtectionType;
import com.projetpaparobin.objects.extinguishers.ExtinguisherID;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

public class ExtinguisherInputDialogHandler implements IExtinguisherCreatorListener {

	private static double width = 180;
	
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	
	private Dialog<ExtinguisherID> inputDialog;
	private ComboBox<String> protectionType;
	private ComboBox<UIColor> colorComboBox;
	private CheckBox isNew;
	private TextField fabricationYear, number, extinguisherType, brand;
	
	public ExtinguisherInputDialogHandler() {
		inputDialog = new Dialog<ExtinguisherID>();
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefWidth(width);
		
		number = new TextField();
		number.setPromptText("Number");
		number.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		colorComboBox = new ComboBox<UIColor>(FXCollections.observableArrayList(UIElements.DEFAULT_EXTINGUISHER_COLORS));
		colorComboBox.setValue(UIColor.LIGHTRED);
		colorComboBox.setPrefWidth(width);		
		
		extinguisherType = new TextField();
		extinguisherType.setPromptText("Extinguisher type");		
		TextFields.bindAutoCompletion(extinguisherType, Stream.of(EExtinguisherType.values()).map(t -> t.getName()).collect(Collectors.toSet()));
		extinguisherType.textProperty().addListener((observable, oldValue, newValue) -> {
			EExtinguisherType type = EExtinguisherType.getEnum(newValue);
			if(type != EExtinguisherType.OTHER) {
				colorComboBox.setValue(type.getColor());
			}
		});
		
		protectionType = new ComboBox<String>(FXCollections.observableArrayList(Stream.of(EProtectionType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList())));	
		protectionType.setPrefWidth(width);
		
		fabricationYear = new TextField();
		fabricationYear.setPromptText("Fabrication year");
		fabricationYear.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		fabricationYear.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isBlank()) {
				long intVal = Long.parseLong(newValue);
				setErrorStyle((intVal < UIElements.MIN_FABRICATION_YEAR || intVal > UIElements.MAX_FABRICATION_YEAR), fabricationYear);
			}
			
		});		
		
		brand = new TextField();
		brand.setPromptText("Brand");
		brand.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		isNew = new CheckBox("Is new");	
		
		dialogPane.setContent(new VBox(8, number, extinguisherType, protectionType, fabricationYear, brand, colorComboBox, isNew));
		
		final Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
		okButton.addEventFilter(ActionEvent.ACTION, 
			event -> {
				if(!checkValuesOk()) {
					event.consume();
				}
			}
		);
		
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String numberVal = (number.getText().isBlank()) ? ExtinguisherCreator.getDefaultZoneNumber() : number.getText();
				String extinguisherTypeVal = (extinguisherType.getText().isBlank()) ? EExtinguisherType.getDefaultExtinguisherType() : extinguisherType.getText();
				EProtectionType protectionTypeVal = EProtectionType.getEnum(protectionType.getValue());
				int fabricationYearVal = (fabricationYear.getText().isBlank()) ? 1995 : Integer.parseInt(fabricationYear.getText());
				String brandVal = (brand.getText().strip().isBlank()) ? UIElements.DEFAULT_BRAND : brand.getText().strip().toUpperCase();
				boolean isNewVal = isNew.isSelected();
				UIColor colorVal = colorComboBox.getValue();
				
				resetFields();
				
				return new ExtinguisherID(numberVal, extinguisherTypeVal, protectionTypeVal, fabricationYearVal, brandVal, isNewVal, colorVal);
			} 
			if(button == ButtonType.CANCEL) {
				resetFields();
				extinguisherCreator.canceled();
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(false);
		extinguisherCreator.addListener(this);
	}

	@Override
	public void handleExtinguisherCreatorEvent(EExtinguisherEvents event) {
		switch (event) {
		case CREATING_NEW_EXTINGUISHER:
			break;
		case SETTING_NAME:
			updateFields();
			Optional<ExtinguisherID> response = inputDialog.showAndWait();
			if(!response.isEmpty()) {
				extinguisherCreator.setExtinguisherID(response.get());
			}
			break;		
		case FINISHED_CREATING_EXTINGUISHER:
			break;
		case CANCELED:
			break;
		}

	}
	
	private void updateFields() {
		switch (extinguisherCreator.getCurrentExtinguisher().getZone().getId().getAreaType()) {
		case ZB:
			protectionType.setValue(EProtectionType.PG.toString());
			break;
		case ZIP:
			protectionType.setValue(EProtectionType.PIP.toString());
			break;
		}
	}
	
	private void resetFields() {
		fabricationYear.setText("");
		number.setText("");
		extinguisherType.setText("");
		brand.setText("");
		protectionType.setValue(EProtectionType.values()[0].toString());
		isNew.setSelected(false);
		
		setErrorStyle(false, fabricationYear);
		setErrorStyle(false, number);
		setErrorStyle(false, extinguisherType);
		setErrorStyle(false, brand);
	}
	
	private boolean checkValuesOk() {
		boolean allValuesOk = true;
		
		if(!fabricationYear.getText().isBlank()) {
			long intVal = Long.parseLong(fabricationYear.getText());
			if(intVal < UIElements.MIN_FABRICATION_YEAR || intVal > UIElements.MAX_FABRICATION_YEAR) {
				allValuesOk = false;
				setErrorStyle(true, fabricationYear);
			} else {
				setErrorStyle(false, fabricationYear);
			}
		}
		
		return allValuesOk;
	}
	
	private void setErrorStyle(boolean hasError, TextField fabricationYear) {
		if(hasError) {
			fabricationYear.setStyle(""
					+ "-fx-text-box-border: #DBB1B1; "
					+ "-fx-control-inner-background: #FFF0F0; "
					+ "-fx-focus-color: #FF2020; "
					+ "-fx-faint-focus-color: #FF202020;");
		} else {
			fabricationYear.setStyle(null);
		}
	}
	
}

