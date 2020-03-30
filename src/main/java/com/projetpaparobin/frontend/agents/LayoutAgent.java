package com.projetpaparobin.frontend.agents;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.projetpaparobin.documents.PDFHandler;
import com.projetpaparobin.utils.UIElements;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LayoutAgent extends VBox {

	private static PDFHandler pdfHandler = PDFHandler.getInstance();
	private Image image;
	private ImageView imageView;
	
	public LayoutAgent(String imagePath, int pageNum, int height, int width, MainUI handler) {
		super();
		this.setMaxSize(width, height);
		this.setAlignment(Pos.CENTER);
		
		try {
			BufferedImage bufImage = pdfHandler.getImageFromPDF(imagePath, pageNum);
			image = SwingFXUtils.toFXImage(bufImage, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
		double heightRatio = image.getHeight() / height;
		double widthRatio = image.getWidth() / width;
		
		System.out.println(heightRatio + " " + widthRatio);
		if(heightRatio > widthRatio) {
			imageView.setFitHeight(height);
		} else {
			imageView.setFitWidth(width);
		}
		
		this.getChildren().addAll(imageView);		
	}
	
}
