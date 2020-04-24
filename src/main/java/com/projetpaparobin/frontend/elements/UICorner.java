package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UICorner extends UIElement {
	
	private Circle circle;		
	private Zone zone;
	private static double lineWidth = 1.0;
	
	public UICorner(Zone zone, Point p, double radius, Color rimColor, UIColor fillColor, ViewLayoutAgent viewLayoutAgent) {
		super(p.getX(), p.getY(), rimColor, fillColor, viewLayoutAgent);
		this.zone = zone;
		prepareImage(radius);
	}

	@Override
	public void drawShape() {
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(lineWidth);
		canvasGC.strokeOval(
				(posX - circle.getRadius()) * viewLayoutAgent.getCanvasWidth(), 
				posY * viewLayoutAgent.getCanvasHeight() - circle.getRadius() * viewLayoutAgent.getCanvasWidth(), 
				circle.getRadius() * 2 * viewLayoutAgent.getCanvasWidth(), 
				circle.getRadius() * 2 * viewLayoutAgent.getCanvasWidth());
		
		if(fillColor != null) {
			canvasGC.setFill(fillColor.getColor());
			canvasGC.fillOval(
					(posX - circle.getRadius()) * viewLayoutAgent.getCanvasWidth(), 
					posY * viewLayoutAgent.getCanvasHeight() - circle.getRadius() * viewLayoutAgent.getCanvasWidth(), 
					circle.getRadius() * 2 * viewLayoutAgent.getCanvasWidth(), 
					circle.getRadius() * 2 * viewLayoutAgent.getCanvasWidth());
		}		
	}

	public void prepareImage(double radius) {		
		circle = new Circle(posX, posY, radius);
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return circle.contains(posX, posY);
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		circle = new Circle(posX * viewLayoutAgent.getCanvasWidth(), posY * viewLayoutAgent.getCanvasHeight(), circle.getRadius());
	}
	
	public Zone getZone() {
		return zone;
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
