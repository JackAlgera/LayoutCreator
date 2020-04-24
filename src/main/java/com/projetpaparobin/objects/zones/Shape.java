package com.projetpaparobin.objects.zones;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.shape.Polygon;

public class Shape {

	private ArrayList<Point> points;
	
	@JsonIgnore
	private Polygon area;
	
	public Shape() {
		this.area = new Polygon();
		this.points = new ArrayList<Point>();
	}
	
	public Shape(ArrayList<Point> points) {
		this.area = new Polygon();
		this.points = points;
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
		
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
		updateArea();
	}

	public Polygon getArea() {
		return area;
	}
	
	public void updateArea() {
		area.getPoints().clear();
		for (Point point : points) {
			area.getPoints().addAll(point.getX(), point.getY());
		}
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public boolean containsPoint(double posX, double posY) {
		return area.contains(posX, posY);
	}
	
	public boolean isEmpty() {
		return area.getPoints().isEmpty();
	}
	
}
