package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.io.File;
import java.nio.file.Paths;

import com.projetpaparobin.documents.applicationstate.ApplicationStatePersister;
import com.projetpaparobin.utils.UIElements;

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
				new FileChooser.ExtensionFilter(UIElements.SAVE_FILE_TYPE.toUpperCase() + " files (*." + UIElements.SAVE_FILE_TYPE + ")", "*." + UIElements.SAVE_FILE_TYPE),
				new FileChooser.ExtensionFilter("Tout", "*"));
				
		fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().normalize().toFile());
	}
	
	public boolean showSaveDialog() {
		fileChooser.setTitle("Enregistrer sous");
		File saveFile = fileChooser.showSaveDialog(primaryStage);
		if(saveFile != null) {
			ApplicationStatePersister.saveState(saveFile);
			return true;
		}
		return false;
	}
	
	public void showLoadDialog() {
		fileChooser.setTitle("Ouvrir");
		File loadFile = fileChooser.showOpenDialog(primaryStage);
		if(loadFile != null) {
			ApplicationStatePersister.loadState(loadFile);
		}
	}
}
