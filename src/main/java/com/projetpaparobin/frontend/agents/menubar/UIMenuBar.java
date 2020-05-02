package com.projetpaparobin.frontend.agents.menubar;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.AreYouSureInputDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.FileSaveInputDialogHandler;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class UIMenuBar extends MenuBar {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	
	private FileSaveInputDialogHandler fileSaveInputDialog;
	private AreYouSureInputDialogHandler areYouSureInputDialog;
	
	private Menu fileMenu;
	
	public UIMenuBar(Stage primaryStage) {
		super();
		this.fileSaveInputDialog = new FileSaveInputDialogHandler(primaryStage);
		this.areYouSureInputDialog = new AreYouSureInputDialogHandler(primaryStage, "Confirmation De Suppression", "Etes-vous sûr de vouloir tout supprimer ?");
		
		setFileMenu();
		this.getMenus().add(fileMenu);
		
	}
	
	private void setFileMenu() {
		fileMenu = new Menu("Fichier");

		MenuItem newItem = new MenuItem("Nouveau");
		newItem.setOnAction(event -> {
			if(areYouSureInputDialog.showAndWait()) {
				layoutHandler.fullReset();
				zoneCreator.canceled();			
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
	
}
