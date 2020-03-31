package com.projetpaparobin.frontend.agents.layout;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.projetpaparobin.documents.PDFHandler;
import com.projetpaparobin.frontend.shapes.Circle;
import com.projetpaparobin.utils.UIElements;
import com.projetpaparobin.zones.Point;
import com.projetpaparobin.zones.creators.IZoneCreatorListener;
import com.projetpaparobin.zones.creators.ZoneCreator;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ViewLayoutAgent extends StackPane implements IViewLayoutAgent, EventHandler<MouseEvent> {

	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static PDFHandler pdfHandler = PDFHandler.getInstance();
	private Image image;
	private ImageView imageView;
	private Canvas canvas;
	
	private PresentationLayoutAgent pres;
	
	public ViewLayoutAgent(String imagePath, int pageNum, int height, int width, PresentationLayoutAgent pres) {
		super();
		this.setMaxSize(width, height);
		this.setAlignment(Pos.CENTER);
		this.pres = pres;		
		
		try {
			BufferedImage bufImage = pdfHandler.getImageFromPDF(imagePath, pageNum);
			image = SwingFXUtils.toFXImage(bufImage, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		double heightRatio = image.getHeight() / height;
		double widthRatio = image.getWidth() / width;
		double aspectRatio = image.getWidth() / image.getHeight();
		
		if(heightRatio > widthRatio) {
			imageView.setFitHeight(height);
			canvas = new Canvas(height * aspectRatio, height);
		} else {
			imageView.setFitWidth(width);
			canvas = new Canvas(width, width / aspectRatio);
		}
		
		canvas.setMouseTransparent(true);
		
		this.getChildren().addAll(imageView ,canvas);		
	}

	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void cleanCanvas() {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	@Override
	public void handle(MouseEvent event) {
		System.out.println("Button:" + event.getButton() + " x=" + event.getX() + " y=" + event.getY());
		zoneCreator.addPoint(new Point(event.getX(), event.getY()));		
	}

}
