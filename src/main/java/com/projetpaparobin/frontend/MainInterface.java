package com.projetpaparobin.frontend;

import com.projetpaparobin.documents.PDFHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterface extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
    	String pathImage = "D:\\Projets Java\\Projet Papa Robin\\image.pdf";
    	int height = 800;
    	
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
