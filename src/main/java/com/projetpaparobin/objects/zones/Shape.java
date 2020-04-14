package com.projetpaparobin.objects.zones;

import java.awt.Polygon;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Shape {

	private ArrayList<Point> points;
	
	@JsonIgnore
	private Polygon area;
	
	public Shape() {
		this.points = new ArrayList<Point>();
		this.area = new Polygon();
		updateArea();
	}
	
	public void deletePoint(Point point) {
		this.points.remove(point);
		updateArea();
	}
	
	public void addPoint(Point point) {
		this.points.add(point);
		updateArea();
	}
		
	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
		updateArea();
	}

	public void updateArea() {
		int[] xPoints = new int[points.size()];
		int[] yPoints = new int[points.size()];
		
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			xPoints[i] = (int) p.getX();
			yPoints[i] = (int) p.getY();
		}
		
		area = new Polygon(xPoints, yPoints, points.size());
	}

	public Polygon getArea() {
		return area;
	}
	
}
