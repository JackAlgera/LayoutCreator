package com.projetpaparobin.frontend.elements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class UIElement {

	protected Color rimColor, fillColor;
	protected GraphicsContext canvasGC;
	protected double posX, posY;
	
	public UIElement(double posX, double posY, Color rimColor, Color fillColor, Canvas canvas) {
		this.posX = posX;
		this.posY = posY;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.canvasGC = canvas.getGraphicsContext2D();
	}
	
	public void addCanvas(Canvas canvas) {
		this.canvasGC = canvas.getGraphicsContext2D();
	}
	
	public void translateShape(double newPosX, double newPosY) {
		this.posX = newPosX;
		this.posY = newPosY;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public abstract void drawShape();
	
}
