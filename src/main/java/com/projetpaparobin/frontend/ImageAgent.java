package com.projetpaparobin.frontend;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.projetpaparobin.documents.PDFHandler;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ImageAgent extends VBox {

	private static PDFHandler pdfHandler = PDFHandler.getInstance();
	private Image image;
	private ImageView imageView;
	
	public ImageAgent(String imagePath, int pageNum, int height) {
		super();
		
		try {
			BufferedImage bufImage = pdfHandler.getImageFromPDF(imagePath, pageNum);
			image = SwingFXUtils.toFXImage(bufImage, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(height);
		
		this.getChildren().addAll(imageView);		
	}
	
}
