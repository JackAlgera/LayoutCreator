package com.projetpaparobin.frontend.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Square extends UIShape {

	private double width, height;
	
	public Square(double posX, double posY, double width, double height, Color rimColor, Color fillColor, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void drawShape() {
		canvasGC.setFill(fillColor);
		canvasGC.fillRect(posX, posY, width, height);
	}

}
