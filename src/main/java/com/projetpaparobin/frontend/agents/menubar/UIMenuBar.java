package com.projetpaparobin.frontend.agents.menubar;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.PDFHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.AreYouSureInputDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.FileSaveInputDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser.ChosenInputFilesPOJO;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser.FileChooseInputDialogHandler;
import com.projetpaparobin.frontend.agents.settings.SettingsStage;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class UIMenuBar extends MenuBar {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static PDFHandler pdfHandler = PDFHandler.getInstance();
	
	private FileSaveInputDialogHandler fileSaveInputDialog;
	private AreYouSureInputDialogHandler areYouSureInputDialog;
	private SettingsStage settingsStage;
	
	private Menu fileMenu, optionsMenu;
	private Stage primaryStage;
	
	public UIMenuBar(Stage primaryStage) {
		super();
		this.fileSaveInputDialog = new FileSaveInputDialogHandler(primaryStage);
		this.areYouSureInputDialog = new AreYouSureInputDialogHandler(primaryStage, "Confirmation De Suppression", "Etes-vous sûr de vouloir tout supprimer ?");
		this.settingsStage = new SettingsStage(primaryStage);
		
		setFileMenu();
		setOptionsMenu();
		this.getMenus().addAll(fileMenu, optionsMenu);
		
	}
	
	private void setFileMenu() {
		fileMenu = new Menu("Fichier");

		MenuItem newItem = new MenuItem("Nouveau");
		newItem.setOnAction(event -> {
			if(areYouSureInputDialog.showAndWait()) {				
				FileChooseInputDialogHandler fileChooser = new FileChooseInputDialogHandler(primaryStage);
				ChosenInputFilesPOJO file = fileChooser.showAndWait();

				layoutHandler.fullReset();
				zoneCreator.canceled();			
				
		    	if(file != null && !file.getLayoutPDFPath().isBlank() && !file.getExcelTemplatePath().isBlank() && file.getLayoutPageNum() > 0) {
					try {
						BufferedImage bufImage = pdfHandler.getImageFromPDF(file.getLayoutPDFPath(), file.getLayoutPageNum());
						layoutHandler.setBufImage(bufImage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	} 						
			}
		});
		
		MenuItem saveItem = new MenuItem("Sauvegarder");
		saveItem.setOnAction(event -> {
			fileSaveInputDialog.showSaveDialog();
		});
		
		MenuItem loadItem = new MenuItem("Ouvrir");
		loadItem.setOnAction(event -> {
			fileSaveInputDialog.showLoadDialog();
		});		
		
		fileMenu.getItems().addAll(newItem, saveItem, loadItem);		
	}
	
	private void setOptionsMenu() {
		optionsMenu = new Menu("Options");
		
		MenuItem settingsItem = new MenuItem("Paramètres");
		settingsItem.setOnAction(event -> {
			settingsStage.show();
		});
		
		optionsMenu.getItems().addAll(settingsItem);		
	}
	
}
