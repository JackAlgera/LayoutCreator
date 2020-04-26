package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Window;

public class AreYouSureInputDialogHandler extends DialogHandlerAbs {

	private Dialog<Boolean> inputDialog;
	
	public AreYouSureInputDialogHandler(Window primaryStage, String title, String warningText) {
		super(primaryStage);
		inputDialog = new Dialog<Boolean>();
		inputDialog.setTitle(title);
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		Label layoutLabel = new Label(warningText);
		
		dialogPane.setContent(layoutLabel);
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				return true;
			} 
			if(button == ButtonType.CANCEL) {
				return false;
			}
			return false;
		});
		
		inputDialog.setResizable(false);
	}
	
	public Boolean showAndWait() {
		if(inputDialog.getOwner() == null) {
			inputDialog.initOwner(primaryStage);
		}
		return inputDialog.showAndWait().get();
	}
	
}
