package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmCloseEventHandler {

	private FileSaveInputDialogHandler fileSaveInputDialog;
	private EventHandler<WindowEvent> confirmCloseEventHandler;
	
	public ConfirmCloseEventHandler(Stage primaryStage) {
		fileSaveInputDialog = new FileSaveInputDialogHandler(primaryStage);
		
		confirmCloseEventHandler = event -> {
			Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous sauvegarder votre travail avant de quitter ?");

			ButtonType exitButton = new ButtonType("Quitter", ButtonBar.ButtonData.OK_DONE);
			ButtonType saveAndExitButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.NO);
			ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);			
			
			closeConfirmation.getButtonTypes().clear();
			closeConfirmation.getButtonTypes().add(saveAndExitButton);	
			closeConfirmation.getButtonTypes().add(exitButton);	
			closeConfirmation.getButtonTypes().add(cancelButton);			
			
			closeConfirmation.setHeaderText("Quitter l'application");
			closeConfirmation.initModality(Modality.APPLICATION_MODAL);
			closeConfirmation.initOwner(primaryStage);
			
			Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
			System.out.println(closeResponse.get().getText());
			if(closeResponse.get() == saveAndExitButton) {
				fileSaveInputDialog.showSaveDialog();
				return;
			} if(closeResponse.get() == exitButton) {
				return;
			} if(closeResponse.get() == cancelButton) {
				event.consume();
				return;
			}
			
		};		
	}
	
	public EventHandler<WindowEvent> getConfirmCloseEventHandler() {
		return confirmCloseEventHandler;
	}
	
}
