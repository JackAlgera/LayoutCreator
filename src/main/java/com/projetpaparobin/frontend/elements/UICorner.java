package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UICorner extends UIElement {
	
	private Circle circle;		
	private Point point;
	private Zone zone;
	private static double lineWidth = 1.0;
	
	public UICorner(Zone zone, Point p, double radius, Color rimColor, Color fillColor, Canvas canvas) {
		super(p.getX(), p.getY(), rimColor, fillColor, canvas);
		this.point = p;
		this.zone = zone;
		prepareImage(radius);
	}

	@Override
	public void drawShape() {
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(lineWidth);
		canvasGC.strokeOval(posX - circle.getRadius(), posY - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
		
		if(fillColor != null) {
			canvasGC.setFill(fillColor);
			canvasGC.fillOval(posX - circle.getRadius(), posY - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2);
		}		
	}

	public void prepareImage(double radius) {		
		circle = new Circle(posX, posY, radius);
		circle.setStroke(rimColor);
		circle.setStrokeWidth(0.8);		
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return circle.contains(posX, posY);
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		circle = new Circle(posX, posY, circle.getRadius());
		point.setX(newPosX);
		point.setY(newPosY);
	}
	
	public Zone getZone() {
		return zone;
	}
}
