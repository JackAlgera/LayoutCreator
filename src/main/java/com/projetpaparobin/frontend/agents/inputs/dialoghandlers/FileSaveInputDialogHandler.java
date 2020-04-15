package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.io.File;
import java.nio.file.Paths;

import com.projetpaparobin.documents.applicationstate.ApplicationStatePersister;

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
	
	public void showSaveDialog() {
		fileChooser.setTitle("Enregistrer sous");
		File saveFile = fileChooser.showSaveDialog(primaryStage);
		if(saveFile != null) {
			ApplicationStatePersister.saveState(saveFile);
		}
	}
	
	public void showLoadDialog() {
		fileChooser.setTitle("Ouvrir");
		File loadFile = fileChooser.showOpenDialog(primaryStage);
		if(loadFile != null) {
			ApplicationStatePersister.loadState(loadFile);
		}
	}
}
