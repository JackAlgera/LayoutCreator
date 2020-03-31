
package com.projetpaparobin.zones;

public class Point {


    
    // Parameters
    
    private double x,y;
    
    // Constructor
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    // Methods
    
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
