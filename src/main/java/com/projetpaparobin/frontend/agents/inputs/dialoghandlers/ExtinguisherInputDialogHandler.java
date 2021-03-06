package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.controlsfx.control.textfield.TextFields;

import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
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
import javafx.stage.Window;

public class ExtinguisherInputDialogHandler extends DialogHandlerAbs implements ITabHandler, IExtinguisherCreatorListener {

	private static double DEFAULT_SPACE_BETWEEN_INPUTS = 8;
	private static double width = 180;

	private static TabHandler tabHandler = TabHandler.getInstance();
	private static ExtinguisherCreator extinguisherCreator = null;
	
	private Dialog<ExtinguisherID> inputDialog;
	private ComboBox<String> protectionType;
	private ComboBox<UIColor> colorComboBox;
	private CheckBox isNew;
	private TextField fabricationYear, number, extinguisherType, brand, local;
		
	public ExtinguisherInputDialogHandler(Window primaryStage) {
		super(primaryStage);
		inputDialog = new Dialog<ExtinguisherID>();
		inputDialog.setTitle("Ajouter un extincteur");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefWidth(width);
		
		number = new TextField();
		number.setPromptText("Num�rotation extincteur");
		number.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		local = new TextField();
		local.setPromptText("Local");
		
		colorComboBox = new ComboBox<UIColor>(FXCollections.observableArrayList(UIElements.DEFAULT_EXTINGUISHER_COLORS));
		colorComboBox.setValue(EExtinguisherType.getDefaultExtinguisherType().getColor());
		colorComboBox.setPrefWidth(width);		
		
		extinguisherType = new TextField();
		extinguisherType.setPromptText("Type d'extincteur");		
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
		protectionType.valueProperty().addListener((observable, oldValue, newValue) -> {
			EProtectionType type = EProtectionType.getEnum(newValue);
			if(type.equals(EProtectionType.PC)) {
				dialogPane.setContent(new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, number, extinguisherType, protectionType, local, fabricationYear, brand, colorComboBox, isNew));
				if(dialogPane.getScene() != null) {
					dialogPane.getScene().getWindow().sizeToScene();
				}
			} else {
				dialogPane.setContent(new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, number, extinguisherType, protectionType, fabricationYear, brand, colorComboBox, isNew));
				if(dialogPane.getScene() != null) {
					dialogPane.getScene().getWindow().sizeToScene();
				}
			}
		});
		
		fabricationYear = new TextField();
		fabricationYear.setPromptText("Ann�e de fabrication");
		fabricationYear.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		fabricationYear.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isBlank()) {
				long intVal = Long.parseLong(newValue);
				setErrorStyle((intVal < UIElements.MIN_FABRICATION_YEAR || intVal > UIElements.MAX_FABRICATION_YEAR), fabricationYear);
			}			
		});		
		
		brand = new TextField();
		brand.setPromptText("Marque");
		brand.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		isNew = new CheckBox("Nouveau extincteur?");	
		
		VBox vbox = new VBox(DEFAULT_SPACE_BETWEEN_INPUTS, number, extinguisherType, protectionType, fabricationYear, brand, colorComboBox, isNew);
		vbox.setFillWidth(true);
		
		dialogPane.setContent(vbox);
		
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
				String extinguisherTypeVal = (extinguisherType.getText().isBlank()) ? EExtinguisherType.getDefaultExtinguisherType().getName() : extinguisherType.getText();
				String protectionTypeVal = EProtectionType.getEnum(protectionType.getValue()).getType();
				int fabricationYearVal = (fabricationYear.getText().isBlank()) ? UIElements.DEFAULT_YEAR : Integer.parseInt(fabricationYear.getText());
				String brandVal = (brand.getText().strip().isBlank()) ? UIElements.DEFAULT_BRAND : brand.getText().strip().toUpperCase();
				boolean isNewVal = isNew.isSelected();
				UIColor colorVal = colorComboBox.getValue();
				String localValue = (local == null) ? "" : local.getText();
				
				number.setText(ExtinguisherCreator.getDefaultZoneNumberAndOne());
				extinguisherType.setText(extinguisherTypeVal);
				fabricationYear.setText("" + fabricationYearVal);
				brand.setText(brandVal);
				local.setText(localValue);
								
				return new ExtinguisherID(numberVal, extinguisherTypeVal, protectionTypeVal, fabricationYearVal, brandVal, isNewVal, colorVal, localValue);
			} 
			if(button == ButtonType.CANCEL) {
				if(extinguisherCreator != null) {
					extinguisherCreator.canceled();
				}
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(true);
		tabHandler.addListener(this);
	}

	@Override
	public void handleExtinguisherCreatorEvent(EExtinguisherEvents event) {
		switch (event) {
		case CREATING_NEW_EXTINGUISHER:
			break;
		case SETTING_NAME:
			if(inputDialog.getOwner() == null) {
				inputDialog.initOwner(primaryStage);
			}
			updateFields();
			Optional<ExtinguisherID> response = inputDialog.showAndWait();
			if(!response.isEmpty() && extinguisherCreator != null) {
				extinguisherCreator.setExtinguisherID(response.get());
			}
			break;		
		case FINISHED_CREATING_EXTINGUISHER:
			break;
		case CANCELED:
			break;
		}
	}

	public void resetFields() {
		fabricationYear.setText("");
		number.setText("");
		extinguisherType.setText("");
		brand.setText("");
		protectionType.setValue(EProtectionType.values()[0].toString());
		isNew.setSelected(false);
		colorComboBox.setValue(EExtinguisherType.getDefaultExtinguisherType().getColor());
		local.setText("");
		
		setErrorStyle(false, fabricationYear);
		setErrorStyle(false, number);
		setErrorStyle(false, extinguisherType);
		setErrorStyle(false, brand);
	}
	
	@Override
	public void handleTabHAndlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
			break;
		case CHANGED_SELECTED_TAB:
			if(extinguisherCreator != null) {
				extinguisherCreator.removeListener(this);
			}
			
			extinguisherCreator = tabHandler.getSelectedLayoutHandler().getExtinguisherCreator();

			if(extinguisherCreator != null) {
				extinguisherCreator.addListener(this);
			}
			break;
		case REMOVED_TAB:
			break;
		case FULL_RESET:
			resetFields();
			break;
		}
	}
	
	private void updateFields() {
		switch (extinguisherCreator.getCurrentExtinguisher().getZone().getId().getAreaType()) {
		case ZB:
			if(EProtectionType.getEnum(protectionType.getValue()).equals(EProtectionType.PC)) {
				protectionType.setValue(EProtectionType.PC.toString());
			} else {
				protectionType.setValue(EProtectionType.PG.toString());
			}
			break;
		case ZIP:
			protectionType.setValue(EProtectionType.PIP.toString());
			break;
		}
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
	
	private void setErrorStyle(boolean hasError, TextField textField) {
		if(hasError) {
			textField.getStyleClass().add("validation-error");
		} else {
			textField.getStyleClass().setAll("text-field", "text-input");
		}
	}
	
}

