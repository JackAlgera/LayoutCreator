package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UICorner extends UIElement {
	
	private Circle circle;		
	private UIElement uiElement;
	private static double lineWidth = 1.0;
	
	public UICorner(LayoutHandler layoutHandler, UIElement uiElement, Point p, double radius, Color rimColor, UIColor fillColor, ViewLayoutAgent viewLayoutAgent) {
		super(layoutHandler, p.getX(), p.getY(), false, rimColor, fillColor, viewLayoutAgent);
		this.uiElement = uiElement;
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
		super.drawShape();
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
		circle = new Circle(posX, posY, circle.getRadius());
	}

	public UIElement getUiElement() {
		return uiElement;
	}

	@Override
	public void removeSelf() {
	}

	@Override
	public void resize(double newPosY) {
		uiElement.resize(newPosY);
	}
}
