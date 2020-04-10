package com.projetpaparobin.frontend.agents.inputs.filechooser;

import java.io.File;

import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileChooseInputDialogHandler {
	
	private FileChooser fileChooser;
	private Dialog<ChosenInputFilesPOJO> inputDialog;
	
	public FileChooseInputDialogHandler(Window primaryStage) {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choix fichie");
		inputDialog = new Dialog<ChosenInputFilesPOJO>();
		inputDialog.setTitle("Layout Editor");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		Label layoutLabel = new Label("PDF avec plan");
		TextField layoutPath = new TextField();
		layoutPath.setPromptText("Nom fichier");
		layoutPath.setOnMouseClicked((event) -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				layoutPath.setText(file.getAbsolutePath());
			}
		});
		
		TextField pageNum = new TextField();
		pageNum.setPromptText("Num page");
		pageNum.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		
		Label excelLabel = new Label("Excel");
		TextField excelPath = new TextField();
		excelPath.setPromptText("Nom fichier");
		excelPath.setOnMouseClicked((event) -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				excelPath.setText(file.getAbsolutePath());
			}
		});
		
		dialogPane.setContent(new HBox(8, new VBox(8, layoutLabel, layoutPath, pageNum), new VBox(8, excelLabel, excelPath)));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				String layoutVal = layoutPath.getText();				
				int pageNumVal = (pageNum.getText().isBlank()) ? 1 : Integer.parseInt(pageNum.getText());
				String excelVal = excelPath.getText();
				
				pageNum.setText("");	
				layoutPath.setText("");
				excelPath.setText("");				
				
				return new ChosenInputFilesPOJO(pageNumVal, layoutVal, excelVal);
			} 
			if(button == ButtonType.CANCEL) {
				return new ChosenInputFilesPOJO(-1, "", "");
			}
			return null;
		});
		
		inputDialog.setResizable(false);
	}
	
	public ChosenInputFilesPOJO showAndWait() {
		return inputDialog.showAndWait().get();
	}
	
}
