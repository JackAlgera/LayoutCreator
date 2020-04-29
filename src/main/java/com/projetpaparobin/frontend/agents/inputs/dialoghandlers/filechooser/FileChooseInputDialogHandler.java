package com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser;

import java.io.File;

import com.projetpaparobin.documents.preferences.DAOPreferencesImpl;
import com.projetpaparobin.documents.preferences.PreferencesPOJO;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.DialogHandlerAbs;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FileChooseInputDialogHandler extends DialogHandlerAbs {

	public final String NBR_EXTINGUISHERS_SHEET_NAME = "dernière page";
	public final String PARC_INDUSTRIELLE_SHEET_NAME = "Parc activité industrielle";
	public final String PARC_TERTIAIRE_SHEET_NAME = "Parc activité tertiaire";
	public final String RECENSEMENT_SHEET_NAME = "Recensement";
	
	private FileChooser fileChooser;
	private Dialog<ChosenInputFilesPOJO> inputDialog;
	private DAOPreferencesImpl daoPrefs;
	
	public FileChooseInputDialogHandler(Window primaryStage) {
		super(primaryStage);
		daoPrefs = new DAOPreferencesImpl();
		PreferencesPOJO prefs = daoPrefs.getPrefs();
		
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choix du fichier PDF");
		inputDialog = new Dialog<ChosenInputFilesPOJO>();
		inputDialog.setTitle("Editeur de plan");
		
		DialogPane dialogPane = inputDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
				
		Label layoutLabel = new Label("PDF du plan");
		TextField layoutPath = new TextField();		
		layoutPath.setText(prefs.getLayoutPDFPath());		
		layoutPath.setPromptText("Nom du fichier");
		layoutPath.setOnMouseClicked((event) -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				layoutPath.setText(file.getAbsolutePath());
			}
		});
		
		TextField pageNum = new TextField();
		pageNum.setPromptText("Numéro de la page du plan");
		pageNum.setTextFormatter(new TextFormatter<String>(UIElements.getNumberFilter()));
		if(prefs.getLayoutPageNum() > 0) {
			pageNum.setText("" + prefs.getLayoutPageNum());
		}
		
		Label excelLabel = new Label("Excel de travail");
		TextField excelPath = new TextField();		
		excelPath.setText(prefs.getExcelTemplatePath());		
		excelPath.setPromptText("Nom du fichier");
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
				
				daoPrefs.setPrefs(new PreferencesPOJO(excelVal, layoutVal, pageNumVal,
						 NBR_EXTINGUISHERS_SHEET_NAME, 
						 PARC_INDUSTRIELLE_SHEET_NAME, 
						 PARC_TERTIAIRE_SHEET_NAME, 
						 RECENSEMENT_SHEET_NAME));
				
				pageNum.setText("");	
				layoutPath.setText("");
				excelPath.setText("");				
				
				return new ChosenInputFilesPOJO(excelVal, layoutVal, pageNumVal);
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
