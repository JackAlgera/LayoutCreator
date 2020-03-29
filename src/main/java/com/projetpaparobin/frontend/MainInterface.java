package com.projetpaparobin.frontend;

import com.projetpaparobin.documents.PDFHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterface extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
    	int height = 800;
    	String pathImage = "C:\\Users\\robbi\\Desktop\\Travail papa\\initial.pdf";
    	
		ImageAgent image = new ImageAgent(pathImage, 1, height);
		
		Scene scene = new Scene(image);
		
		primaryStage.setTitle("Projet papa Robaing");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
    public static void main( String[] args )
    {    
    	launch(args);
    }
	
}
