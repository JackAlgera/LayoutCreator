package com.projetpaparobin.frontend.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UIRectangle extends UIShape {

	private double width, height;
	
	public UIRectangle(double posX, double posY, double width, double height, Color rimColor, Color fillColor, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void drawShape() {
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(4.0);
		canvasGC.strokeRect(posX, posY, width, height);
		
		canvasGC.setFill(fillColor);
		canvasGC.fillRect(posX, posY, width, height);
	}

}
