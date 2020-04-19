package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
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

public class UIZoneText extends UIElement {

	private static double LINE_WIDTH = 1.5;
	
	private Rectangle hitbox;
	private Zone zone;

	private WritableImage drawnImage;
	private Bounds bounds;
		
	public UIZoneText(Zone zone, Canvas canvas) {
		super(	(zone.getTextAreaPosition() == null) ? zone.getShape().getArea().getBounds2D().getCenterX() : zone.getTextAreaPosition().getX(),
				(zone.getTextAreaPosition() == null) ? zone.getShape().getArea().getBounds2D().getMinY() : zone.getTextAreaPosition().getY(),
				zone.getRimColor(), zone.getFillColor(), canvas);
		this.zone = zone;
		prepareImage();
	}
	
	@Override
	public void drawShape() {	
		canvasGC.drawImage(drawnImage, posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0));	
	}
	
	public void update() {
		prepareImage();
	}
	
	private void prepareImage() {
		int scale = 5;
	
		StackPane sPane = new StackPane();		
		Text txt = new Text(zone.getAreaName());
		txt.setFont(UIElements.LAYOUT_FONT);
		txt.setFill(zone.getRimColor());	
		
		sPane.getChildren().addAll(txt);		
		bounds = sPane.getBoundsInLocal();
		
		Rectangle whiteRect = new Rectangle(bounds.getWidth() * 1.1, bounds.getHeight() * 1.1);
		whiteRect.setStroke(zone.getRimColor());
		whiteRect.setStrokeWidth(LINE_WIDTH);
		whiteRect.setFill(Color.WHITE);
		
		sPane.getChildren().remove(txt);
		sPane.getChildren().addAll(whiteRect, txt);

		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		
		drawnImage = sPane.snapshot(params, null);
		
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(bounds.getWidth());
		view.setFitHeight(bounds.getHeight());
		drawnImage = view.snapshot(null, null);
		
		hitbox = new Rectangle(posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0), bounds.getWidth(), bounds.getHeight());
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return hitbox.contains(posX, posY);
	}
	
	public Zone getZone() {
		return zone;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		zone.setTextAreaPosition(new Point(newPosX, newPosY));
		hitbox = new Rectangle(posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0), bounds.getWidth(), bounds.getHeight());
	}
}
