package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class UIElement {

	protected static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	protected UIColor fillColor;
	protected Color rimColor;
	protected GraphicsContext canvasGC;
	protected ViewLayoutAgent viewLayoutAgent;
	protected double posX, posY;
	protected boolean isSelected;
	
	public UIElement(double posX, double posY, Color rimColor, UIColor fillColor, ViewLayoutAgent viewLayoutAgent) {
		this.posX = posX;
		this.posY = posY;
		this.viewLayoutAgent = viewLayoutAgent;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.isSelected = false;
		this.canvasGC = viewLayoutAgent.getCanvas().getGraphicsContext2D();
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
	
	public abstract void setIsSelected(boolean isSelected);
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public abstract void removeSelf();
	
	public abstract void drawShape();
	
	public abstract boolean containsPoint(double posX, double posY);
	
}
