package com.projetpaparobin.frontend.agents.settings;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsVariable extends HBox {
	
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	private static FileChooser fileChooser = new FileChooser();
	private static DirectoryChooser folderChooser = new DirectoryChooser();
	
	private EPreferencesValues key;
	
	private Label label;
	private TextField textField;
	
	public SettingsVariable(Stage stage, EPreferencesValues key, String prefValue, EFileType type) {
		super(8);
		this.setAlignment(Pos.CENTER_LEFT);
		this.label = new Label(key.getDisplayValue());
		this.textField = new TextField(prefValue);
		
		switch (type) {
		case FILE:
			textField.setOnMouseClicked((event) -> {
				fileChooser.setTitle(key.getDisplayValue());
				String keyVal = FilenameUtils.getFullPath(dao.getKeyValue(key));
				String workspacePath = FilenameUtils.getFullPath(dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH));
				if(keyVal != null && !keyVal.isBlank()) {
					fileChooser.setInitialDirectory(new File(keyVal));
				} else if(workspacePath != null && !workspacePath.isBlank()) {
					fileChooser.setInitialDirectory(new File(workspacePath));
				}
				File file = fileChooser.showOpenDialog(stage);
				if(file != null) {
					textField.setText(file.getAbsolutePath());
				}
			});
			break;
		case FOLDER:
			textField.setOnMouseClicked((event) -> {
				folderChooser.setTitle(key.getDisplayValue());
				String keyVal = FilenameUtils.getFullPath(dao.getKeyValue(key));
				String workspacePath = FilenameUtils.getFullPath(dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH));
				if(keyVal != null && !keyVal.isBlank()) {
					fileChooser.setInitialDirectory(new File(keyVal));
				} else if(workspacePath != null && !workspacePath.isBlank()) {
					fileChooser.setInitialDirectory(new File(workspacePath));
				}
				File file = folderChooser.showDialog(stage);
				if(file != null) {
					textField.setText(file.getAbsolutePath());
				}
			});
			break;
		}

		this.key = key;
		setHgrow(label, Priority.ALWAYS);
		setHgrow(textField, Priority.ALWAYS);
		
		this.getChildren().addAll(label, textField);
	}
	
	public String getValue() {
		return textField.getText();
	}
	
	public EPreferencesValues getKey() {
		return key;
	}
	
	public void updateTextField() {
		textField.setText(dao.getKeyValue(key));
	}
	
}
