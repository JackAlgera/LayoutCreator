package com.projetpaparobin.frontend;

import com.projetpaparobin.frontend.agents.MainUI;
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
				
    	int height = 800;
    	int width = 1000;
    	
    	if(file != null && !file.getLayoutPath().isBlank() && !file.getExcelPath().isBlank()) {
	    	int imageNbr = file.getPageNbr();
			String layoutPath = file.getLayoutPath();
			String excelPath = file.getExcelPath();

    		MainUI layout = new MainUI(height, width, layoutPath, imageNbr, excelPath);		
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
