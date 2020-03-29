package com.projetpaparobin.zones;

import java.util.ArrayList;

public class Shape {

	// Parameters
	
	private int aire;
	private ArrayList<Point> points;
	
	//Constructor	
	
	public Shape(int aire, ArrayList<Point> points) {
		this.aire = aire;
		this.points = points;
	}

	// Methods
	
	public void deletePoint(Point point) {
		this.points.remove(point);
	}
	
	public void addPoint(Point point) {
		this.points.add(point);
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

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

}
