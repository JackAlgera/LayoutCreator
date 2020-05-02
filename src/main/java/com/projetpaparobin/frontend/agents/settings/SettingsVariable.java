package com.projetpaparobin.frontend.agents.settings;

import com.projetpaparobin.documents.preferences.EPreferencesValues;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SettingsVariable extends HBox {
	
	private Label label;
	private TextField textField;
	
	public SettingsVariable(EPreferencesValues pref, String prefValue) {
		super(8);
		this.setAlignment(Pos.CENTER_LEFT);
		this.label = new Label(pref.getDisplayValue());
		this.textField = new TextField(prefValue);
		setHgrow(label, Priority.ALWAYS);
		setHgrow(textField, Priority.ALWAYS);
		
		this.getChildren().addAll(label, textField);
	}
	
	public String getValue() {
		return textField.getText();
	}
	
}
