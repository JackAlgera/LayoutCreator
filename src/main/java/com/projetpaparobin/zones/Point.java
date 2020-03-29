/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projetpaparobin.zones;

/**
 *
 * @author Robinvi
 */
public class Point {


    
    // Parameters
    
    private int x,y;
    
    // Constructor
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Methods
    
    public void setX(int posX){
        this.x=posX;
    }
    
    public void setY(int posY){
        this.y=posY;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    
}
