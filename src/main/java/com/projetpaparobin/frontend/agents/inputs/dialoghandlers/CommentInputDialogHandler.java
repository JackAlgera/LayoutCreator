package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;

import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.objects.creators.comments.CommentCreator;
import com.projetpaparobin.objects.creators.comments.ECommentEvent;
import com.projetpaparobin.objects.creators.comments.ICommentCreatorListener;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class CommentInputDialogHandler extends DialogHandlerAbs implements ITabHandler, ICommentCreatorListener {

	private static double width = 180;

	private static TabHandler tabHandler = TabHandler.getInstance();
	private static CommentCreator commentCreator = null;
	
	private Dialog<String> inputDialog;
	private TextField text;
	
	public CommentInputDialogHandler(Window primaryStage) {
		super(primaryStage);
		inputDialog = new Dialog<String>();
		inputDialog.setTitle("Nouveau commentaire");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialogPane.setPrefWidth(width);
		
		text = new TextField();
		text.setPromptText("Commentaire");
			
		dialogPane.setContent(new VBox(8, text));
		
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String textVal = text.getText();
				text.setText("");
				
				return textVal;
			} else if(button == ButtonType.CANCEL) {
				text.setText("");
				if(commentCreator != null) {
					commentCreator.canceled();
				}
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(false);
		tabHandler.addListener(this);
	}
			
	@Override
	public void handleCommentCreatorEvent(ECommentEvent event) {
		switch (event) {
		case CREATING_NEW_COMMENT:
			break;
		case SETTING_TEXT:
			if(inputDialog.getOwner() == null) {
				inputDialog.initOwner(primaryStage);
			}
			Optional<String> response = inputDialog.showAndWait();
			if(!response.isEmpty() && commentCreator != null) {				
				commentCreator.setCommentText(response.get());
			}
			break;
		case FINISHED_CREATING_COMMENT:
			break;
		case CANCELED:
			break;
		}
	}

	@Override
	public void handleTabHAndlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
			break;
		case CHANGED_SELECTED_TAB:
			if(commentCreator != null) {
				commentCreator.removeListener(this);
			}
			
			commentCreator = tabHandler.getSelectedLayoutHandler().getCommentCreator();

			if(commentCreator != null) {
				commentCreator.addListener(this);
			}
			break;
		case REMOVED_TAB:
			break;
		}
	}
	
}
