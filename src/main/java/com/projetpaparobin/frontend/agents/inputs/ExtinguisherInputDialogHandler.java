package com.projetpaparobin.frontend.agents.inputs;

import java.util.Optional;

import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.extinguishers.ExtinguisherID;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ExtinguisherInputDialogHandler implements IExtinguisherCreatorListener {
	
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	
	private Dialog<ExtinguisherID> inputDialog;
	
	public ExtinguisherInputDialogHandler() {
		inputDialog = new Dialog<ExtinguisherID>();
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		TextField number = new TextField();
		number.setPromptText("Number");
		number.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		TextField extinguisherType = new TextField();
		extinguisherType.setPromptText("Extinguisher type");
		extinguisherType.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		TextField protectionType = new TextField();
		protectionType.setPromptText("Protection type");
		protectionType.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		TextField fabricationYear = new TextField();
		fabricationYear.setPromptText("Fabrication year");
		fabricationYear.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		TextField brand = new TextField();
		brand.setPromptText("Brand");
		brand.setTextFormatter(new TextFormatter<String>(UIElements.getLetterFilter()));
		
		CheckBox isNew = new CheckBox("Is new");

		ObservableList<Color> colorList = FXCollections.observableArrayList(Color.RED, Color.BLUE, Color.GREEN);
		ComboBox<Color> colorComboBox = new ComboBox<Color>(colorList);
				
		dialogPane.setContent(new VBox(8, number, extinguisherType, protectionType, fabricationYear, brand, isNew));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String numberVal = number.getText();
				String extinguisherTypeVal = extinguisherType.getText();
				String protectionTypeVal = protectionType.getText();
				String fabricationYearVal = fabricationYear.getText();
				String brandVal = brand.getText();
				boolean isNewVal = isNew.isSelected();
				Color colorVal = Color.RED;
				
				number.setText("");
				extinguisherType.setText("");
				protectionType.setText("");
				fabricationYear.setText("");
				brand.setText("");
				isNew.setSelected(false);
				
				if(numberVal.isBlank()) {
					numberVal = "-1";
				}
				if(fabricationYearVal.isBlank()) {
					fabricationYearVal = "-1";
				}
				
				return new ExtinguisherID(Integer.parseInt(numberVal), extinguisherTypeVal, protectionTypeVal, Integer.parseInt(fabricationYearVal), brandVal, isNewVal, colorVal);
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
	
}

