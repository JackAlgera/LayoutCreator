package com.projetpaparobin.frontend.elements.shapes;

import com.projetpaparobin.frontend.elements.UIElement;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UICircle extends UIElement {
	 
	private double radius;
	private double lineWidth = 1.0;
	
	public UICircle(double posX, double posY, double radius, Color rimColor, Color fillColor, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.radius = radius;
	}
	
	public UICircle(double posX, double posY, double radius, double lineWidth, Color rimColor, Color fillColor, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.radius = radius;
		this.lineWidth = lineWidth;
	}
	
	@Override
	public void drawShape() {
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(lineWidth);
		canvasGC.strokeOval(posX - radius, posY - radius, radius * 2, radius * 2);
		
		if(fillColor != null) {
			canvasGC.setFill(fillColor);
			canvasGC.fillOval(posX - radius, posY - radius, radius * 2, radius * 2);
		}		
	}
	
	public boolean containsPoint(double posX, double posY) {
		double dX = Math.pow(this.posX - posX, 2);
		double dY = Math.pow(this.posY - posY, 2);
		double d = Math.sqrt(dX + dY);
		
		return (d <= radius); 
	}
}
