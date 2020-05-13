package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import com.projetpaparobin.documents.applicationstate.ApplicationStatePersister;
import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;
import com.projetpaparobin.utils.UIElements;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileSaveInputDialogHandler {
		
	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private FileChooser fileChooser;
	private Stage primaryStage;
	
	public FileSaveInputDialogHandler(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		fileChooser = new FileChooser();
		fileChooser.setTitle("Enregistrer sous");
		
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter(UIElements.SAVE_FILE_TYPE.toUpperCase() + " files (*." + UIElements.SAVE_FILE_TYPE + ")", "*." + UIElements.SAVE_FILE_TYPE),
				new FileChooser.ExtensionFilter("Tout", "*"));
	}
	
	public boolean showSaveDialog() {
		fileChooser.setTitle("Enregistrer sous");
		fileChooser.setInitialDirectory(new File(FilenameUtils.getFullPath(dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH))));
		File saveFile = fileChooser.showSaveDialog(primaryStage);
		if(saveFile != null) {
			ApplicationStatePersister.saveState(saveFile);
			return true;
		}
		return false;
	}
	
	public void showLoadDialog() {
		fileChooser.setTitle("Ouvrir");
		fileChooser.setInitialDirectory(new File(FilenameUtils.getFullPath(dao.getKeyValue(EPreferencesValues.WORKSPACE_PATH))));
		File loadFile = fileChooser.showOpenDialog(primaryStage);
		if(loadFile != null) {
			ApplicationStatePersister.loadState(loadFile);
		}
	}
}
