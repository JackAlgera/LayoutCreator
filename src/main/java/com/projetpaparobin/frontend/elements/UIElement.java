package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class UIElement {

	protected static double RESIZE_SENSITIVITY = 0.5; // TODO
	protected static double RESIZE_POINT_RADIUS = 0.01;
	protected static UIColor RESIZE_FILL_COLOR = UIColor.WHITE;
	protected static UIColor RESIZE_RIM_COLOR = UIColor.BLACK;
	
	protected static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	protected UIColor fillColor;
	protected Color rimColor;
	protected GraphicsContext canvasGC;
	protected ViewLayoutAgent viewLayoutAgent;
	protected double posX, posY;
	protected boolean isSelected;	
	
	private boolean hasResizeCorner;
	protected UICorner resizeCorner;
	
	public UIElement(double posX, double posY, boolean hasResizeCorner, Color rimColor, UIColor fillColor, ViewLayoutAgent viewLayoutAgent) {
		this.posX = posX;
		this.posY = posY;
		this.viewLayoutAgent = viewLayoutAgent;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.isSelected = false;
		this.canvasGC = viewLayoutAgent.getCanvas().getGraphicsContext2D();
		this.hasResizeCorner = hasResizeCorner;
	}
		
	public void translateShape(double newPosX, double newPosY) {
		double deltaX = newPosX - posX;
		double deltaY = newPosY - posY;
		this.posX = newPosX;
		this.posY = newPosY;
		if(hasResizeCorner) {
			resizeCorner.translateShape(resizeCorner.getPosX() + deltaX, resizeCorner.getPosY() + deltaY);
		}
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if(hasResizeCorner) {
			resizeCorner.setIsSelected(isSelected);
		}
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public abstract void removeSelf();
	
	public void drawShape() {
		if(isSelected && hasResizeCorner) {
			resizeCorner.drawShape();
		}		
	}
	
	public UICorner getResizeCorner() {
		return resizeCorner;
	}
	
	public void initResizeCorner(Point p) {
		this.resizeCorner = new UICorner(this, p, RESIZE_POINT_RADIUS, RESIZE_RIM_COLOR.getColor(), RESIZE_FILL_COLOR, viewLayoutAgent);
	}
	
	public abstract void resize(double newPosY);
	
	protected void translateResizeCorner(double newPosX, double newPosY) {
		resizeCorner.translateShape(newPosX, newPosY);
	}
	
	public abstract boolean containsPoint(double posX, double posY);
		
}
