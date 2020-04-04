package com.projetpaparobin.objects.zones;

public class Point {

    private double x, y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double posX){
        this.x=posX;
    }
    
    public void setY(double posY){
        this.y=posY;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }  
    
}
