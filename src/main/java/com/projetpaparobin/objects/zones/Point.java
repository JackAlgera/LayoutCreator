package com.projetpaparobin.objects.zones;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Point {

    private double x, y;
    
    public Point() {
	}
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }  
    
    @JsonIgnore
    public static double getDistanceSquared(Point p1, Point p2) {
    	return Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2);
    }
    
}
