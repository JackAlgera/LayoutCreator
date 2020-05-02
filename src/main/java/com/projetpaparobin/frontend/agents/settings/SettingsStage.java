package com.projetpaparobin.frontend.agents.settings;

import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsStage {

	private static String SETTINGS_TITLE = "Paramètres";
	private static double WIDTH = 600, HEIGHT = 600;
	
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private ScrollPane panel;
	private Stage primaryStage, stage;
	
	
	public SettingsStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		panel = new ScrollPane();
		panel.setFitToWidth(true);
		
		SettingsVariable excelTemplatePath = new SettingsVariable(EPreferencesValues.EXCEL_TEMPLATE_PATH, dao.getKeyValue(EPreferencesValues.EXCEL_TEMPLATE_PATH));
		SettingsVariable layoutPDFPath = new SettingsVariable(EPreferencesValues.LAYOUT_PDF_PATH, dao.getKeyValue(EPreferencesValues.LAYOUT_PDF_PATH));
		SettingsVariable layoutPageNum = new SettingsVariable(EPreferencesValues.LAYOUT_PAGE_NUM, dao.getKeyValue(EPreferencesValues.LAYOUT_PAGE_NUM));

		panel.setContent(new VBox(5, excelTemplatePath, layoutPDFPath, layoutPageNum));
		
		Scene scene = new Scene(panel, WIDTH, HEIGHT);
		
		stage = new Stage();
		stage.setTitle(SETTINGS_TITLE);
		stage.setScene(scene);
	}
	
	public void show() {
		stage.setX(primaryStage.getX() + (primaryStage.getWidth() - WIDTH) / 2);
		stage.setY(primaryStage.getY() + (primaryStage.getHeight() - HEIGHT) / 2);
		stage.show();
	}
	
}
