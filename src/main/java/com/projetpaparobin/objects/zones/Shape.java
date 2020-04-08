package com.projetpaparobin.objects.zones;

import java.awt.Polygon;
import java.util.ArrayList;

public class Shape {

	private int aire;
	private ArrayList<Point> points;
	private Polygon area;
	
	public Shape() {
		aire = 0;
		points = new ArrayList<Point>();
		area = new Polygon();
	}
	
	public Shape(int aire, ArrayList<Point> points) {
		this.aire = aire;
		this.points = points;
		area = new Polygon();
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

	public int getAire() {
		return aire;
	}

	public void setAire(int aire) {
		this.aire = aire;
	}

	public ArrayList<Point> getPoints() {
		return points;
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
