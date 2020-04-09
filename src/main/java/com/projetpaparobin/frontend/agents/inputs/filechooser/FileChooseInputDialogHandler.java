package com.projetpaparobin.frontend.agents.inputs.filechooser;

import java.io.File;

import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileChooseInputDialogHandler {
	
	private FileChooser fileChooser;
	private Dialog<ChosenFilePOJO> inputDialog;
	
	public FileChooseInputDialogHandler(Window primaryStage) {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir pdf avec l'image à éditer");
		inputDialog = new Dialog<ChosenFilePOJO>();
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		TextField fileName = new TextField();
		fileName.setPromptText("Nom fichier");
		fileName.setOnMouseClicked((event) -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				fileName.setText(file.getAbsolutePath());
			}
		});
		
		TextField pageNum = new TextField();
		pageNum.setPromptText("Num page");
		pageNum.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		dialogPane.setContent(new VBox(8, fileName, pageNum));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String fileNameVal = fileName.getText();				
				int pageNumVal = Integer.parseInt(pageNum.getText());
				
				fileName.setText("");		
				pageNum.setText("");				
				
				return new ChosenFilePOJO(pageNumVal, fileNameVal);
			} 
			if(button == ButtonType.CANCEL) {
				return new ChosenFilePOJO(1, "");
			}
			return null;
		});
		
		inputDialog.setResizable(false);
	}
	
	public ChosenFilePOJO showAndWait() {
		return inputDialog.showAndWait().get();
	}
	
}
