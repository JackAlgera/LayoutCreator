package com.projetpaparobin.frontend.agents.layout;

import com.projetpaparobin.documents.ILayoutHandlerListener;
import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
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

public class ViewLayoutAgent extends StackPane implements ITabHandler, IViewLayoutAgent, ILayoutHandlerListener {

	private static TabHandler tabHandler = TabHandler.getInstance();
	private static MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	private LayoutHandler layoutHandler;
	
	private Image image;
	private ImageView imageView;
	private Canvas canvas;
	private ZoneInputDialogHandler zoneInputDialog;
	private ExtinguisherInputDialogHandler extinguisherInputDialog;
	private CommentInputDialogHandler commentInputDialog;
	
	private PresentationLayoutAgent pres;
	private double canvasRatio;
		
	public ViewLayoutAgent() {
		layoutHandler = null;
	}
	
	public ViewLayoutAgent(Window primaryStage, double width, double height, PresentationLayoutAgent pres) {
		super();
		this.setMaxSize(width, height);
		this.setMinSize(width, height);
		this.setAlignment(Pos.CENTER);
		this.pres = pres;		
		layoutHandler = null;
		zoneInputDialog = new ZoneInputDialogHandler(primaryStage, pres);
		extinguisherInputDialog = new ExtinguisherInputDialogHandler(primaryStage);
		commentInputDialog = new CommentInputDialogHandler(primaryStage);
		
		mouseInputHandler.setViewLayoutAgent(this);
		imageView = null;
		tabHandler.addListener(this);
	}
	
	public void updateLayoutImage(LayoutHandler layoutHandler) {		
		if(this.layoutHandler != null) {
			this.layoutHandler.removeListener(this);
		}
		
		this.layoutHandler = layoutHandler;
		
		if(layoutHandler == null) {
			return;
		}
		layoutHandler.addListener(this);
		if(layoutHandler.getBufImage() == null) {
			imageView = null;
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
		if(canvas != null) {
			canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		}
	}
	
	public double getCanvasWidth() {
		return imageView.getBoundsInLocal().getWidth();
	}
	
	public double getCanvasHeight() {
		return imageView.getBoundsInLocal().getHeight();
	}
	
	public void resizePanel(double width, double height) {
		if(imageView == null) {
			return;
		}
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
		updateLayoutImage(layoutHandler);
	}

	public double getCanvasRatio() {
		return canvasRatio;
	}

	@Override
	public void handleTabHAndlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
			break;
		case CHANGED_SELECTED_TAB:
			layoutHandler = tabHandler.getSelectedLayoutHandler();
			updateLayoutImage(layoutHandler);
			pres.updateShapes();
			break;
		case REMOVED_TAB:
			break;
		case FULL_RESET:
			break;
		}
	}
	
}
