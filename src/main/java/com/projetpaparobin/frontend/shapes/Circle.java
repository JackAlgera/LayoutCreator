package com.projetpaparobin.frontend.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Circle extends UIShape {
	 
	private double radius;
	
	public Circle(double posX, double posY, double radius, Color rimColor, Color fillColor, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.radius = radius;
	}
	
	@Override
	public void drawShape() {
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(1.0);
		canvasGC.strokeOval(posX - radius, posY - radius, radius * 2, radius * 2);
		
		if(fillColor != null) {
			canvasGC.setFill(fillColor);
			canvasGC.fillOval(posX - radius, posY - radius, radius * 2, radius * 2);
		}		
	}
	
}
