package com.projetpaparobin.frontend.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class UIShape {

	protected Color rimColor, fillColor;
	protected GraphicsContext canvasGC;
	protected double posX, posY;
	
	public UIShape(double posX, double posY, Color rimColor, Color fillColor, Canvas canvas) {
		this.posX = posX;
		this.posY = posY;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.canvasGC = canvas.getGraphicsContext2D();
	}
	
	public void addCanvas(Canvas canvas) {
		this.canvasGC = canvas.getGraphicsContext2D();
	}
	
	public abstract void drawShape();
	
}
