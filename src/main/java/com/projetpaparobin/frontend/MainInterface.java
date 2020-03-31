package com.projetpaparobin.frontend;

import com.projetpaparobin.frontend.agents.MainUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterface extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
    	int height = 800;
    	int width = 1000;
    	String layoutImage = "D:\\Projets Java\\Projet Papa Robin\\image.pdf";
    	
		MainUI layout = new MainUI(height, width, layoutImage);
		
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
