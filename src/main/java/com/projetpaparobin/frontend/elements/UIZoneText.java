package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;

public class UIZoneText extends UIElement {

	private static double TEXT_WIDTH = 0.2;
	private static double LINE_WIDTH = 1.5;
	
	private Rectangle hitbox;
	private Zone zone;

	private WritableImage drawnImage;
		
	public UIZoneText(Zone zone, ViewLayoutAgent viewLayoutAgent) {
		super(	(zone.getTextAreaPosition() == null) ? zone.getShape().getArea().getBoundsInLocal().getCenterX() : zone.getTextAreaPosition().getX(),
				(zone.getTextAreaPosition() == null) ? zone.getShape().getArea().getBoundsInLocal().getMinY() : zone.getTextAreaPosition().getY(),
				zone.getRimColor(), zone.getFillColor(), viewLayoutAgent);
		this.zone = zone;
		prepareImage();
	}
	
	@Override
	public void drawShape() {	
		canvasGC.drawImage(drawnImage, posX * viewLayoutAgent.getCanvasWidth() - (drawnImage.getWidth() / 2.0), posY * viewLayoutAgent.getCanvasHeight() - (drawnImage.getHeight() / 2.0));	
	}
	
	public void update() {
		prepareImage();
	}
	
	private void prepareImage() {
		int scale = 5;
	
		StackPane sPane = new StackPane();		
		Text txt = new Text(zone.getDisplayText());
		txt.setFont(UIElements.LAYOUT_FONT);
		txt.setFill(zone.getRimColor());	
		
		sPane.getChildren().addAll(txt);		
		Bounds bounds = sPane.getBoundsInLocal();
		
		Rectangle whiteRect = new Rectangle(bounds.getWidth() * 1.1, bounds.getHeight() * 1.1);
		whiteRect.setStroke(zone.getRimColor());
		whiteRect.setStrokeWidth(LINE_WIDTH);
		whiteRect.setFill(Color.WHITE);
		
		sPane.getChildren().setAll(whiteRect, txt);

		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		
		drawnImage = sPane.snapshot(params, null);		
		
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(viewLayoutAgent.getCanvasWidth() * TEXT_WIDTH);
		view.setFitHeight(viewLayoutAgent.getCanvasWidth() * TEXT_WIDTH * (bounds.getHeight() / bounds.getWidth()));
		drawnImage = view.snapshot(null, null);

		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
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
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}
	
	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void removeSelf() {
		// TODO Auto-generated method stub
		
	}
	
}
