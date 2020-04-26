package com.projetpaparobin.frontend;

import com.projetpaparobin.documents.applicationstate.ApplicationStatePersister;
import com.projetpaparobin.frontend.agents.MainUI;
import com.projetpaparobin.frontend.agents.inputs.KeyboardInputHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.ConfirmCloseEventHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser.ChosenInputFilesPOJO;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser.FileChooseInputDialogHandler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainInterface extends Application {

	private MainUI layout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
    	double height = 800;
    	double width = 1000;
    	
		primaryStage.setTitle("Projet papa Robaing");
		primaryStage.setMaximized(false);
		primaryStage.setResizable(true);
		primaryStage.getIcons().add(new Image("/icons/Application_Icon.png"));
		addResizeListener(primaryStage);
		primaryStage.setOnCloseRequest(new ConfirmCloseEventHandler(primaryStage).getConfirmCloseEventHandler());		

		layout = new MainUI();		
		Scene scene = new Scene(layout);		
		primaryStage.setScene(scene);
		
		FileChooseInputDialogHandler fileChooser = new FileChooseInputDialogHandler(primaryStage);
		ChosenInputFilesPOJO file = fileChooser.showAndWait();		
    	
    	if(file != null && !file.getLayoutPDFPath().isBlank() && !file.getExcelTemplatePath().isBlank() && file.getLayoutPageNum() > 0) {
    		layout = new MainUI(primaryStage, width, height, file.getExcelTemplatePath(), file.getLayoutPDFPath(), file.getLayoutPageNum());		
    		layout.setOnKeyPressed(KeyboardInputHandler.getInstance());
    		scene = new Scene(layout);
    		scene.getStylesheets().add("stylesheet.css");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    		
    		ApplicationStatePersister.startAutomaticSaver(1);
    	} 
	}
	
	private void addResizeListener(Stage primaryStage) {
		ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> {
			layout.resizePanel(primaryStage.getWidth(), primaryStage.getHeight());
		};
		
		primaryStage.widthProperty().addListener(sizeListener);
		primaryStage.heightProperty().addListener(sizeListener);
	}
	
    public static void main( String[] args )
    {    
    	launch(args);
    }
	
}
