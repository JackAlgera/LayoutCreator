package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;

import com.projetpaparobin.documents.applicationstate.ApplicationStatePersister;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmCloseEventHandler extends DialogHandlerAbs {

	private FileSaveInputDialogHandler fileSaveInputDialog;
	private EventHandler<WindowEvent> confirmCloseEventHandler;
	
	public ConfirmCloseEventHandler(Stage primaryStage) {
		super(primaryStage);
		fileSaveInputDialog = new FileSaveInputDialogHandler(primaryStage);
		
		confirmCloseEventHandler = event -> {
			Alert closeConfirmation = new Alert(Alert.AlertType.NONE);
			closeConfirmation.setTitle("Quitter l'application");
			closeConfirmation.setHeaderText("Voulez-vous sauvegarder votre travail avant de quitter l'application ?");
			
			ButtonType exitButton = new ButtonType("Quitter", ButtonBar.ButtonData.OK_DONE);
			ButtonType saveAndExitButton = new ButtonType("Enregistrer", ButtonBar.ButtonData.NO);
			ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);			
			
			closeConfirmation.getButtonTypes().clear();
			closeConfirmation.getButtonTypes().add(saveAndExitButton);	
			closeConfirmation.getButtonTypes().add(exitButton);	
			closeConfirmation.getButtonTypes().add(cancelButton);			
			
			closeConfirmation.initModality(Modality.APPLICATION_MODAL);
			closeConfirmation.initOwner(primaryStage);
			
			Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
			if(closeResponse.get() == saveAndExitButton) {
				if(!fileSaveInputDialog.showSaveDialog()) {
					event.consume();
				} else {
					ApplicationStatePersister.stopAutomaticSaver();
				}
				return;
			} if(closeResponse.get() == exitButton) {
				ApplicationStatePersister.stopAutomaticSaver();
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
