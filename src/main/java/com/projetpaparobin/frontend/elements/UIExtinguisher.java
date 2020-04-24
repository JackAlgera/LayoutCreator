package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class UIExtinguisher extends UIElement {
	
	private double RADIUS = 0.02;
	private double SELECTED_RADIUS = 0.023;
	
	private Circle circle;		
	private WritableImage drawnImage;
	private Extinguisher ex;
	
	public UIExtinguisher(Extinguisher ex, ViewLayoutAgent viewLayoutAgent) {
		super(ex.getPos().getX(), ex.getPos().getY(), Color.BLACK, ex.getId().getColor(), viewLayoutAgent);
		this.ex = ex;
		prepareImage();
	}

	@Override
	public void drawShape() {
		canvasGC.drawImage(drawnImage, (posX - circle.getRadius()) * viewLayoutAgent.getCanvasWidth(), posY * viewLayoutAgent.getCanvasHeight() - circle.getRadius() * viewLayoutAgent.getCanvasWidth());	
	}

	private void prepareImage() {
		StackPane sPane = new StackPane();		
		Circle drawnCircle;
		
		if(isSelected) {
			circle = new Circle(posX, posY, SELECTED_RADIUS, Color.CYAN);
			drawnCircle = new Circle(posX, posY, SELECTED_RADIUS * viewLayoutAgent.getCanvasWidth(), Color.CYAN);
			drawnCircle.setStroke(Color.GRAY);
			drawnCircle.setStrokeWidth(1.1);	
		} else {
			circle = new Circle(posX, posY, RADIUS, Color.CYAN);
			drawnCircle = new Circle(posX, posY, RADIUS * viewLayoutAgent.getCanvasWidth(), ex.getId().getColor().getColor());
			drawnCircle.setStroke(rimColor);
			drawnCircle.setStrokeWidth(0.8);	
		}	
		
		Text nbrText = new Text("" + ex.getId().getNumber());
		nbrText.setFont(UIElements.EXTINGUISHER_FONT);
		
		sPane.getChildren().setAll(drawnCircle, nbrText);
		
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return circle.contains(posX, posY);
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		circle = new Circle(posX, posY, circle.getRadius());
		ex.setPos(new Point(newPosX, newPosY));
	}

	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
		prepareImage();
	}

	@Override
	public void removeSelf() {
		ex.getZone().removeExtinguisher(ex);
		layoutHandler.removeExtinguisher(ex);
	}
	
}
