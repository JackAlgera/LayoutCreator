package com.projetpaparobin.frontend.agents.layout;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.projetpaparobin.documents.ILayoutHandlerListener;
import com.projetpaparobin.documents.LayoutHandler;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

public class ViewLayoutAgent extends StackPane implements IViewLayoutAgent, ILayoutHandlerListener {

	private static MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	private static PDFHandler pdfHandler = PDFHandler.getInstance();
	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	private Image image;
	private ImageView imageView;
	private Canvas canvas;
	private ZoneInputDialogHandler zoneInputDialog;
	private ExtinguisherInputDialogHandler extinguisherInputDialog;
	private CommentInputDialogHandler commentInputDialog;
	
	private PresentationLayoutAgent pres;
	private double canvasRatio;
		
	public ViewLayoutAgent() {
	}
	
	public ViewLayoutAgent(Window primaryStage, String imagePath, int pageNum, double width, double height, PresentationLayoutAgent pres) {
		super();
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		this.setAlignment(Pos.CENTER);
		this.pres = pres;		
		zoneInputDialog = new ZoneInputDialogHandler(primaryStage);
		extinguisherInputDialog = new ExtinguisherInputDialogHandler(primaryStage);
		commentInputDialog = new CommentInputDialogHandler(primaryStage);
		
		try {
			BufferedImage bufImage = pdfHandler.getImageFromPDF(imagePath, pageNum);
			image = SwingFXUtils.toFXImage(bufImage, null);
			layoutHandler.setBufImage(bufImage);
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
		layoutHandler.addListener(this);
		this.getChildren().addAll(imageView ,canvas);		
		this.canvasRatio = getHeight() / getWidth();
	}
	
	public void updateLayoutImage() {		
		if(layoutHandler.getBufImage() == null) {
			return;
		}
		
		image = SwingFXUtils.toFXImage(layoutHandler.getBufImage(), null);

		double prevHeight = this.getHeight();
		double prevWidth = this.getWidth();
		
		imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseInputHandler);
		imageView.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseInputHandler);
		imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseInputHandler);
		
		resizePanel(prevWidth, prevHeight);
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
		this.canvasRatio = getHeight() / getWidth();
	}

	@Override
	public void layoutImageUpdated() {
		updateLayoutImage();
	}

	public double getCanvasRatio() {
		return canvasRatio;
	}
	
}
