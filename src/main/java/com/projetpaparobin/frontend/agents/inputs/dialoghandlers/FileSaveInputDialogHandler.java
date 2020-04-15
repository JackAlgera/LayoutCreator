package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.io.File;
import java.nio.file.Paths;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileSaveInputDialogHandler {
	
	private FileChooser fileChooser;
	private Stage primaryStage;
	
	public FileSaveInputDialogHandler(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		fileChooser = new FileChooser();
		fileChooser.setTitle("Enregistrer sous");
		
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JCK files (*.jck)", "*.jck"),
				new FileChooser.ExtensionFilter("Tout", "*"));
				
		fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().normalize().toFile());
	}
	
	public File showSaveDialog() {
		fileChooser.setTitle("Enregistrer sous");
		return fileChooser.showSaveDialog(primaryStage);
	}
	
	public File showLoadDialog() {
		fileChooser.setTitle("Ouvrir");
		return fileChooser.showOpenDialog(primaryStage);
	}
	
	
}
