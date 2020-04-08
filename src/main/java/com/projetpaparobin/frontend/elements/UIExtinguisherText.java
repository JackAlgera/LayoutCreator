package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIElements;

import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;

public class UIExtinguisherText extends UIElement {

	private static double Y_OFFSET = -UIElements.LAYOUT_FONT.getSize() * 2.0;
	
	private Rectangle hitbox;
	private Extinguisher ex;

	private WritableImage drawnImage;
	private Bounds bounds;
		
	public UIExtinguisherText(Extinguisher ex, Canvas canvas) {
		super(ex.getPos().getX(), ex.getPos().getY() + Y_OFFSET, Color.BLACK, null, canvas);
		this.ex = ex;
		prepareImage(ex);
	}
	
	@Override
	public void drawShape() {	
		canvasGC.drawImage(drawnImage, posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0));	
	}
	
	private void prepareImage(Extinguisher ex) {
		int scale = 5;
	
		StackPane sPane = new StackPane();		
		Text txt = new Text(ex.getDisplayText());
		txt.setFont(UIElements.LAYOUT_FONT);
			
		sPane.getChildren().addAll(txt);		
		bounds = sPane.getBoundsInLocal();
				
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);
		
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(bounds.getWidth());
		view.setFitHeight(bounds.getHeight());
		params.setTransform(Transform.scale(1, 1));
		drawnImage = view.snapshot(params, null);
		
		hitbox = new Rectangle(posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0), bounds.getWidth(), bounds.getHeight());
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return hitbox.contains(posX, posY);
	}
	
	public Extinguisher getExtinguisher() {
		return ex;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		hitbox = new Rectangle(posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0), bounds.getWidth(), bounds.getHeight());
	}
	
}
