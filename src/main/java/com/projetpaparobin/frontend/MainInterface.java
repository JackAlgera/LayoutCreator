package com.projetpaparobin.frontend;

import com.projetpaparobin.frontend.agents.MainUI;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.ConfirmCloseEventHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser.ChosenInputFilesPOJO;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.filechooser.FileChooseInputDialogHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterface extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {		
		FileChooseInputDialogHandler fileChooser = new FileChooseInputDialogHandler(primaryStage);
		ChosenInputFilesPOJO file = fileChooser.showAndWait();
		
		primaryStage.setOnCloseRequest(new ConfirmCloseEventHandler(primaryStage).getConfirmCloseEventHandler());
		
    	int height = 800;
    	int width = 1000;
    	
    	if(file != null && !file.getLayoutPDFPath().isBlank() && !file.getExcelTemplatePath().isBlank() && file.getLayoutPageNum() > 0) {
    		MainUI layout = new MainUI(primaryStage, height, width, file.getExcelTemplatePath(), file.getLayoutPDFPath(), file.getLayoutPageNum());		
    		Scene scene = new Scene(layout);
    		
    		primaryStage.setTitle("Projet papa Robaing");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	} 
	}
	
    public static void main( String[] args )
    {    
    	launch(args);
    }
	
}
