package com.projetpaparobin.frontend.agents.settings;

import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsStage {

	private static String SETTINGS_TITLE = "Paramètres";
	private static double WIDTH = 600, HEIGHT = 600;
	
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private ScrollPane panel;
	private Stage stage;
	
	
	public SettingsStage(Stage primaryStage) {
		panel = new ScrollPane();
		panel.setContent(new Text("Test"));
		
		Scene scene = new Scene(panel, WIDTH, HEIGHT);
		
		stage = new Stage();
		stage.setTitle(SETTINGS_TITLE);
		stage.setScene(scene);
		stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - stage.getWidth());
		stage.setX(primaryStage.getY() + primaryStage.getHeight() / 2 - stage.getHeight());
	}
	
	public void show() {
		stage.show();
	}
	
}
