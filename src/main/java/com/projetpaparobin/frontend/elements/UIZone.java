package com.projetpaparobin.frontend.elements;

import java.util.ArrayList;

import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UIZone extends UIElement {

	private static double INIT_POINT_RADIUS = 4;
	private static double POINT_RADIUS = 6;
	private static double LINE_WIDTH = 2;
	
	private ArrayList<UICorner> corners; 
	private Zone zone;
	
	private boolean shouldDrawCorners;
	
	public UIZone(Zone zone, Canvas canvas, boolean shouldDrawCorners) {
		super(zone.getShape().getPoints().get(0).getX(), zone.getShape().getPoints().get(0).getY(), zone.getRimColor(), zone.getFillColor(), canvas);
		this.zone = zone;
		this.corners = new ArrayList<UICorner>();
		for (Point point : zone.getShape().getPoints()) {
			corners.add(new UICorner(zone, point, INIT_POINT_RADIUS, Color.BLACK, Color.WHITE, canvasGC.getCanvas()));
		}
		this.shouldDrawCorners = shouldDrawCorners;
	}

	@Override
	public void drawShape() {		
		double[] pointsX = new double[corners.size()];
		double[] pointsY = new double[corners.size()];
		for (int i = 0; i < corners.size(); i++) {
			pointsX[i] = corners.get(i).getPosX();
			pointsY[i] = corners.get(i).getPosY();
		}		
		
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(LINE_WIDTH);
		canvasGC.strokePolygon(pointsX, pointsY, corners.size());
		
		canvasGC.setFill(fillColor);
		canvasGC.fillPolygon(pointsX, pointsY, pointsX.length);
		
		if(shouldDrawCorners) {
			for (UICorner corner : corners) {
				corner.drawShape();
			}
		}	
	}
	
	public void switchPointRadius() {
		for (UICorner corner : corners) {
			corner.prepareImage(POINT_RADIUS);
		}
	}
	
	public boolean shouldDrawCorners() {
		return shouldDrawCorners;
	}
	
	public void setShouldDrawCorners(boolean shouldDrawCorners) {
		this.shouldDrawCorners = shouldDrawCorners;
	}
	
	public UICorner getCorner(double posX, double posY) {
		for (UICorner corner : corners) {
			if(corner.containsPoint(posX, posY)) {
				return corner;
			}
		}
		return null;
	}
	
	@Override
	public boolean containsPoint(double posX, double posY) {
		return zone.containsPoint(posX, posY);
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		double deltaX = newPosX - posX;
		double deltaY = newPosY - posY;
		super.translateShape(newPosX, newPosY);
		for (UICorner corner : corners) {
			corner.translateShape(corner.getPosX() + deltaX, corner.getPosY() + deltaY);
		}
		zone.getShape().updateArea();
	}
	
	public Zone getZone() {
		return zone;
	}
	
}
