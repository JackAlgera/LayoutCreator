package com.projetpaparobin.frontend;

import com.projetpaparobin.frontend.agents.MainUI;
import com.projetpaparobin.frontend.agents.inputs.filechooser.ChosenFilePOJO;
import com.projetpaparobin.frontend.agents.inputs.filechooser.FileChooseInputDialogHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterface extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {		
		FileChooseInputDialogHandler fileChooser = new FileChooseInputDialogHandler(primaryStage);
		ChosenFilePOJO file = fileChooser.showAndWait();
				
    	int height = 800;
    	int width = 1000;
    	String layoutImage = "D:\\Projets Java\\Projet Papa Robin\\image.pdf";
    	int imageNbr = 1;
    	
    	if(file != null) {
    		if(!file.getFilePath().isBlank()) {
        		layoutImage = file.getFilePath();
    		}
    		imageNbr = file.getPageNbr();
    	} 

		MainUI layout = new MainUI(height, width, layoutImage, imageNbr);		
		Scene scene = new Scene(layout);
		
		primaryStage.setTitle("Projet papa Robaing");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
    public static void main( String[] args )
    {    
    	launch(args);
    }
	
}
