package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class FileGenerationDialogHandler extends DialogHandlerAbs {
	
	private Dialog<String> inputDialog;
	
	public FileGenerationDialogHandler(Window primaryStage) {
		super(primaryStage);
		inputDialog = new Dialog<String>();
		inputDialog.setTitle("Génération de fichier");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		TextField fileName = new TextField();
		fileName.setPromptText("File name");
		
		dialogPane.setContent(new VBox(8, fileName));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String fileNameVal = fileName.getText();				
				fileName.setText("");				
				return fileNameVal;
			} 
			if(button == ButtonType.CANCEL) {
				return "";
			}
			return null;
		});
		
		inputDialog.setResizable(false);
	}
	
	public String showAndWait() {
		if(inputDialog.getOwner() == null) {
			inputDialog.initOwner(primaryStage);
		}
		return inputDialog.showAndWait().get();
	}
	
}

