package com.projetpaparobin.frontend.agents.layout;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.projetpaparobin.documents.PDFHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.CommentInputDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.ExtinguisherInputDialogHandler;
import com.projetpaparobin.frontend.agents.inputs.dialoghandlers.ZoneInputDialogHandler;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ViewLayoutAgent extends StackPane implements IViewLayoutAgent {

	private static MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	private static PDFHandler pdfHandler = PDFHandler.getInstance();
	private Image image;
	private ImageView imageView;
	private Canvas canvas;
	private ZoneInputDialogHandler zoneInputDialog;
	private ExtinguisherInputDialogHandler extinguisherInputDialog;
	private CommentInputDialogHandler commentInputDialog;
	
	private PresentationLayoutAgent pres;
	
	public ViewLayoutAgent(String imagePath, int pageNum, double width, double height, PresentationLayoutAgent pres) {
		super();
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		this.setAlignment(Pos.CENTER);
		this.pres = pres;		
		zoneInputDialog = new ZoneInputDialogHandler();
		extinguisherInputDialog = new ExtinguisherInputDialogHandler();
		commentInputDialog = new CommentInputDialogHandler();
		
		try {
			BufferedImage bufImage = pdfHandler.getImageFromPDF(imagePath, pageNum);
			image = SwingFXUtils.toFXImage(bufImage, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseInputHandler);
		imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseInputHandler);
		imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseInputHandler);
		
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
		
		mouseInputHandler.setViewLayoutAgent(this);
		this.getChildren().addAll(imageView ,canvas);		
	}

	public WritableImage getSnapshot(SnapshotParameters params, WritableImage image) {
		return this.snapshot(params, image);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void cleanCanvas() {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public double getCanvasWidth() {
		return imageView.getBoundsInLocal().getWidth();
	}
	
	public double getCanvasHeight() {
		return imageView.getBoundsInLocal().getHeight();
	}
	
	public void resizePanel(double width, double height) {
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		
		double heightRatio = image.getHeight() / height;
		double widthRatio = image.getWidth() / width;
		double aspectRatio = image.getWidth() / image.getHeight();
		
		if(heightRatio > widthRatio) {
			imageView.setFitHeight(height);
			imageView.setFitWidth(-1);
			canvas = new Canvas(height * aspectRatio, height);
		} else {
			imageView.setFitHeight(-1);
			imageView.setFitWidth(width);
			canvas = new Canvas(width, width / aspectRatio);
		}
		
		canvas.setMouseTransparent(true);
		
		this.getChildren().setAll(imageView ,canvas);	
	}

}
