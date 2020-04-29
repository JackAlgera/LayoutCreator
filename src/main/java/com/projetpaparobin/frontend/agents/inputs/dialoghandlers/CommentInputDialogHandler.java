package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import java.util.Optional;

import com.projetpaparobin.objects.creators.comments.CommentCreator;
import com.projetpaparobin.objects.creators.comments.ECommentEvent;
import com.projetpaparobin.objects.creators.comments.ICommentCreatorListener;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class CommentInputDialogHandler extends DialogHandlerAbs implements ICommentCreatorListener {

	private static double width = 180;
	
	private static CommentCreator commentCreator = CommentCreator.getInstance();
	
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
				commentCreator.canceled();
				return null;
			}
			return null;
		});
		
		inputDialog.setResizable(false);
		commentCreator.addListener(this);
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
			if(!response.isEmpty()) {
				commentCreator.setCommentText(response.get());
			}
			break;
		case FINISHED_CREATING_COMMENT:
			break;
		case CANCELED:
			break;
		}
	}
	
}
