package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UIConnection extends UIElement {

	private static double POINT_RADIUS = 0.01;
	
	private UICorner connectionPoint;	
	
	private Line line1, line2;
	private Zone zone;
	private UIZoneText uiText;
	private static double lineWidth = 1.5;
	
	public UIConnection(Zone zone, UIZoneText uiText, Point centerPoint, Color rimColor, ViewLayoutAgent viewLayoutAgent) {
		super(centerPoint.getX(), centerPoint.getY(), rimColor, null, viewLayoutAgent);
		this.uiText = uiText;
		this.zone = zone;
		this.connectionPoint = new UICorner(this, centerPoint, POINT_RADIUS, Color.BLACK, UIColor.WHITE, viewLayoutAgent);
		prepareImage();
	}

	@Override
	public void drawShape() {
		prepareImage();
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(lineWidth);
		canvasGC.strokeLine(line1.getStartX(), line1.getStartY(), line1.getEndX(), line1.getEndY());
		canvasGC.strokeLine(line2.getStartX(), line2.getStartY(), line2.getEndX(), line2.getEndY());
		
		if(isSelected) {
			connectionPoint.drawShape();
		}
	}

	public void prepareImage() {		
		double startX = uiText.getPosX() * viewLayoutAgent.getCanvasWidth();
		double startY = uiText.getPosY() * viewLayoutAgent.getCanvasHeight();
		double endX = connectionPoint.getPosX() * viewLayoutAgent.getCanvasWidth();
		double endY = connectionPoint.getPosY() * viewLayoutAgent.getCanvasHeight();
		
		line1 = new Line(startX, startY, endX, endY);
		
		startX = connectionPoint.getPosX() * viewLayoutAgent.getCanvasWidth();
		startY = connectionPoint.getPosY() * viewLayoutAgent.getCanvasHeight();
		
		if(zone.getShape().getPoints().size() > 1) {
			Point p = new Point(connectionPoint.getPosX(), connectionPoint.getPosY());
			
			double shortestDist = Double.MAX_VALUE;
			
			Point corner1, corner2, temp;
			Point center = p;
			int nbrCorners = zone.getShape().getPoints().size();
			
			for (int i = 0; i < nbrCorners; i++) {
				corner1 = zone.getShape().getPoints().get(i);
				corner2 = zone.getShape().getPoints().get((i + 1) % nbrCorners);
								
				double lambda = ((p.getX() - corner1.getX()) * (corner2.getX() - corner1.getX()) + (p.getY() - corner1.getY()) * (corner2.getY() - corner1.getY())) /
								((corner2.getX() - corner1.getX()) * (corner2.getX() - corner1.getX()) + (corner2.getY() - corner1.getY()) * (corner2.getY() - corner1.getY()));
				
				if(lambda <= 0) {
					temp = corner1;
				} else if(lambda >= 1) {
					temp = corner2;
				} else {
					temp = new Point(	corner1.getX() * (1 - lambda) + lambda * corner2.getX(), 
										corner1.getY() * (1 - lambda) + lambda * corner2.getY());
				}
				
				double dist = Point.getDistanceSquared(p, temp);
				if(dist < shortestDist) {
					shortestDist = dist;
					center = temp;
				}				
			}
			
			endX = center.getX() * viewLayoutAgent.getCanvasWidth();
			endY = center.getY() * viewLayoutAgent.getCanvasHeight();	
			
		} else {
			Point center = zone.getShape().getCenter();
			endX = center.getX() * viewLayoutAgent.getCanvasWidth();
			endY = center.getY() * viewLayoutAgent.getCanvasHeight();			
		}
		
		line2 = new Line(startX, startY, endX, endY);
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return connectionPoint.containsPoint(posX, posY);
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		connectionPoint.translateShape(newPosX, newPosY);
		zone.setTextConnectionCenterPos(new Point(newPosX, newPosY));
	}
		
	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void removeSelf() {
		// TODO Auto-generated method stub
		
	}
	
}
