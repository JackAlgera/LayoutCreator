package com.projetpaparobin.frontend.agents.settings;

import java.util.ArrayList;
import java.util.Arrays;

import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.PreferencesPOJO;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsStage {

	private static String SETTINGS_TITLE = "Paramètres";
	private static double WIDTH = 600, HEIGHT = 600;
	
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private ScrollPane panel;
	private Stage primaryStage, stage;
	
	private ArrayList<SettingsVariable> settingsVariables;
	
	public SettingsStage(Stage primaryStage) {
		this.primaryStage = primaryStage;		
		
		BorderPane root = new BorderPane();
		
		panel = new ScrollPane();
		panel.setFitToWidth(true);
		
		SettingsVariable excelTemplatePath = new SettingsVariable(stage, EPreferencesValues.EXCEL_TEMPLATE_PATH, dao.getKeyValue(EPreferencesValues.EXCEL_TEMPLATE_PATH), EFileType.FILE);
		SettingsVariable workspacePath = new SettingsVariable(stage, EPreferencesValues.WORKSPACE_PATH, dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH), EFileType.FOLDER);

		SettingsVariable nbrExtinguishersSheetName = new SettingsVariable(stage, EPreferencesValues.NBR_EXTINGUISHERS_SHEET_NAME, dao.getKeyValue(EPreferencesValues.NBR_EXTINGUISHERS_SHEET_NAME), EFileType.STRING);
		SettingsVariable parcIndustrielleSheetName = new SettingsVariable(stage, EPreferencesValues.PARC_INDUSTRIELLE_SHEET_NAME, dao.getKeyValue(EPreferencesValues.PARC_INDUSTRIELLE_SHEET_NAME), EFileType.STRING);
		SettingsVariable parcTertiaireSheetName = new SettingsVariable(stage, EPreferencesValues.PARC_TERTIAIRE_SHEET_NAME, dao.getKeyValue(EPreferencesValues.PARC_TERTIAIRE_SHEET_NAME), EFileType.STRING);
		SettingsVariable recensementSheetName = new SettingsVariable(stage, EPreferencesValues.RECENSEMENT_SHEET_NAME, dao.getKeyValue(EPreferencesValues.RECENSEMENT_SHEET_NAME), EFileType.STRING);
		
		settingsVariables = new ArrayList<SettingsVariable>(Arrays.asList(
				excelTemplatePath, workspacePath,
				nbrExtinguishersSheetName, parcIndustrielleSheetName, parcTertiaireSheetName, recensementSheetName
				));
		
		VBox scrollBox = new VBox(5);
		for (SettingsVariable var : settingsVariables) {
			scrollBox.getChildren().add(var);
		}
		panel.setContent(scrollBox);
		
		root.setCenter(panel);
		root.setBottom(getButtonsBox());
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		
		stage = new Stage();
		stage.setTitle(SETTINGS_TITLE);
		stage.setScene(scene);
	}
	
	public void show() {
		stage.setX(primaryStage.getX() + (primaryStage.getWidth() - WIDTH) / 2);
		stage.setY(primaryStage.getY() + (primaryStage.getHeight() - HEIGHT) / 2);
		stage.show();
	}
	
	private HBox getButtonsBox() {
		Button okButton = new Button("Appliquer");
		okButton.setOnAction(event -> {
			PreferencesPOJO prefs = new PreferencesPOJO();
			
			for (SettingsVariable var : settingsVariables) {
				prefs.addKeyValue(var.getKey(), var.getValue());
			}
			
			dao.setPrefs(prefs);
			stage.close();
		});
		
		Button cancelButton = new Button("Annuler");
		cancelButton.setOnAction(event -> {
			stage.close();
		});
		
		HBox buttonsBox = new HBox(8, okButton, cancelButton);
		buttonsBox.setAlignment(Pos.CENTER_RIGHT);
		buttonsBox.setPadding(new Insets(9));
		
		return buttonsBox;
	}
	
}
