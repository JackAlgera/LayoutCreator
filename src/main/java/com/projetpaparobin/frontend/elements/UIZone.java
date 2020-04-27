package com.projetpaparobin.frontend.elements;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.paint.Color;

public class UIZone extends UIElement {
	
	private static double INIT_POINT_RADIUS = 0.005;
	private static double POINT_RADIUS = 0.01;
	private static double LINE_WIDTH = 2;
	
	private ArrayList<UICorner> corners; 
	private Zone zone;
	private UIZoneText zoneText;
	
	public UIZone(Zone zone, ViewLayoutAgent viewLayoutAgent, boolean isSelected) {
		super(zone.getShape().getPoints().get(0).getX(), zone.getShape().getPoints().get(0).getY(), zone.getRimColor(), zone.getFillColor(), viewLayoutAgent);
		this.zone = zone;
		this.zoneText = null;
		this.corners = new ArrayList<UICorner>();
		for (Point point : zone.getShape().getPoints()) {
			corners.add(new UICorner(this, point, INIT_POINT_RADIUS, Color.BLACK, UIColor.WHITE, viewLayoutAgent));
		}
		this.setIsSelected(isSelected);
	}

	@Override
	public void drawShape() {		
		double[] pointsX = new double[corners.size()];
		double[] pointsY = new double[corners.size()];
		for (int i = 0; i < corners.size(); i++) {
			pointsX[i] = corners.get(i).getPosX() * viewLayoutAgent.getCanvasWidth();
			pointsY[i] = corners.get(i).getPosY() * viewLayoutAgent.getCanvasHeight();
		}		
		
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(LINE_WIDTH);
		canvasGC.strokePolygon(pointsX, pointsY, corners.size());
		
		canvasGC.setFill(fillColor.getColor());
		canvasGC.fillPolygon(pointsX, pointsY, corners.size());
		
		if(isSelected) {
			for (UICorner corner : corners) {
				corner.drawShape();
			}
		}	
	}
	
	public void setUiText(UIZoneText zoneText) {
		this.zoneText = zoneText;
	}
	
	public void switchPointRadius() {
		for (UICorner corner : corners) {
			corner.prepareImage(POINT_RADIUS);
		}
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
		if(zoneText != null) {
			zoneText.moveWithZone(deltaX, deltaY);
		}
		updateZone();
	}
	
	public void updateZone() {
		zone.getShape().setPoints(corners.stream().map(c -> new Point(c.getPosX(), c.getPosY())).collect(Collectors.toCollection(ArrayList::new)));
	}
	
	public Zone getZone() {
		return zone;
	}
	
	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void removeSelf() {
		layoutHandler.removeZone(zone);
		for (Extinguisher ex : zone.getExtinguishers()) {
			layoutHandler.removeExtinguisher(ex);
		}
	}
	
}
