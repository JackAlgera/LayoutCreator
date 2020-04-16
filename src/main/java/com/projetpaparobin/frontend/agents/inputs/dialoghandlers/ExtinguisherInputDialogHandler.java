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
	
	public ExtinguisherInputDialogHandler() {
		inputDialog = new Dialog<ExtinguisherID>();
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefWidth(width);
		
		TextField number = new TextField();
		number.setPromptText("Number");
		number.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		ComboBox<UIColor> colorComboBox = new ComboBox<UIColor>(FXCollections.observableArrayList(UIElements.DEFAULT_EXTINGUISHER_COLORS));
		colorComboBox.setValue(UIColor.RED);
		colorComboBox.setPrefWidth(width);		
		
		TextField extinguisherType = new TextField();
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
		
		TextField fabricationYear = new TextField();
		fabricationYear.setPromptText("Fabrication year");
		fabricationYear.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		TextField brand = new TextField();
		brand.setPromptText("Brand");
		brand.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		CheckBox isNew = new CheckBox("Is new");	
		
		dialogPane.setContent(new VBox(8, number, extinguisherType, protectionType, fabricationYear, brand, colorComboBox, isNew));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String numberVal = (number.getText().isBlank()) ? ExtinguisherCreator.getDefaultZoneNumber() : number.getText();
				String extinguisherTypeVal = (extinguisherType.getText().isBlank()) ? EExtinguisherType.getDefaultExtinguisherType() : extinguisherType.getText();
				EProtectionType protectionTypeVal = EProtectionType.getEnum(protectionType.getValue());
				int fabricationYearVal = (fabricationYear.getText().isBlank()) ? 1995 : Integer.parseInt(fabricationYear.getText());
				String brandVal = (brand.getText().strip().isBlank()) ? UIElements.DEFAULT_BRAND : brand.getText().strip().toUpperCase();
				boolean isNewVal = isNew.isSelected();
				UIColor colorVal = colorComboBox.getValue();
				
				number.setText("");
				extinguisherType.setText("");
				protectionType.setValue(EProtectionType.values()[0].toString());
				fabricationYear.setText("");
				brand.setText("");
				isNew.setSelected(false);
				
				return new ExtinguisherID(numberVal, extinguisherTypeVal, protectionTypeVal, fabricationYearVal, brandVal, isNewVal, colorVal);
			} 
			if(button == ButtonType.CANCEL) {
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
		System.out.println(extinguisherCreator.getCurrentExtinguisher().getZone().getId().getAreaType());
		switch (extinguisherCreator.getCurrentExtinguisher().getZone().getId().getAreaType()) {
		case ZB:
			protectionType.setValue(EProtectionType.PG.toString());
			break;
		case ZIP:
			protectionType.setValue(EProtectionType.PIP.toString());
			break;
		}
	}
	
}

