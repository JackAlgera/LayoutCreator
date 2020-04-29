package com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser;

import java.io.File;

import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.PreferencesPOJO;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.DialogHandlerAbs;
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

public class FileChooseInputDialogHandler extends DialogHandlerAbs {
	
	private FileChooser fileChooser;
	private Dialog<ChosenInputFilesPOJO> inputDialog;
	private DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	public FileChooseInputDialogHandler(Window primaryStage) {
		super(primaryStage);
		
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choix du fichier PDF");
		inputDialog = new Dialog<ChosenInputFilesPOJO>();
		inputDialog.setTitle("Editeur de plan");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
				
		Label layoutLabel = new Label("PDF du plan:");
		TextField layoutPath = new TextField();		
		layoutPath.setText(dao.getKeyValue(EPreferencesValues.LAYOUT_PDF_PATH));		
		layoutPath.setPromptText("Nom du fichier");
		layoutPath.setOnMouseClicked((event) -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				layoutPath.setText(file.getAbsolutePath());
			}
		});
		
		Label pageNumLabel = new Label("Numéro de la page du plan:");
		TextField pageNum = new TextField();
		pageNum.setPromptText("Numéro");
		pageNum.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		if(Integer.parseInt(dao.getKeyValue(EPreferencesValues.LAYOUT_PAGE_NUM)) > 0) {
			pageNum.setText("" + dao.getKeyValue(EPreferencesValues.LAYOUT_PAGE_NUM));
		}
		
		Label excelLabel = new Label("Excel de travail:");
		TextField excelPath = new TextField();		
		excelPath.setText(dao.getKeyValue(EPreferencesValues.EXCEL_TEMPLATE_PATH));		
		excelPath.setPromptText("Nom du fichier");
		excelPath.setOnMouseClicked((event) -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				excelPath.setText(file.getAbsolutePath());
			}
		});
		
		dialogPane.setContent(new HBox(8, new VBox(8, layoutLabel, layoutPath, pageNumLabel, pageNum), new VBox(8, excelLabel, excelPath)));
		inputDialog.setResultConverter((ButtonType button) -> {
			if(button == ButtonType.OK) {
				PreferencesPOJO prefs = new PreferencesPOJO();
				prefs.addKeyValue(EPreferencesValues.LAYOUT_PDF_PATH, layoutPath.getText());
				prefs.addKeyValue(EPreferencesValues.LAYOUT_PAGE_NUM, (pageNum.getText().isBlank()) ? "1" : pageNum.getText());
				prefs.addKeyValue(EPreferencesValues.EXCEL_TEMPLATE_PATH, excelPath.getText());

				dao.setPrefs(prefs);
				
				pageNum.setText("");	
				layoutPath.setText("");
				excelPath.setText("");				
				
				return new ChosenInputFilesPOJO(dao.getKeyValue(EPreferencesValues.EXCEL_TEMPLATE_PATH),
						dao.getKeyValue(EPreferencesValues.LAYOUT_PDF_PATH),
						Integer.parseInt(dao.getKeyValue(EPreferencesValues.LAYOUT_PAGE_NUM)));
			} 
			if(button == ButtonType.CANCEL) {
				return new ChosenInputFilesPOJO("", "", -1);
			}
			return null;
		});
		
		inputDialog.setResizable(false);
	}
	
	public ChosenInputFilesPOJO showAndWait() {
		if(inputDialog.getOwner() == null) {
			inputDialog.initOwner(primaryStage);
		}
		return inputDialog.showAndWait().get();
	}
	
}
