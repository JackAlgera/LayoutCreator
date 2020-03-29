package com.projetpaparobin.zones;

import java.awt.Color;

public class Extincteur {

	
	private int number; //from 1 to n 
	private String extinguisherType;
	private String protectionType; 
	private Color squareColor; //either blue,red or yellow
	private Point positionSquare;
	private boolean isNew;
	private Zone zone;
	
	public Extincteur(int number, String extinguisherType, String protectionType, Color squareColor,
			Point positionSquare, boolean isNew, Zone zone) {
		this.number = number;
		this.extinguisherType = extinguisherType;
		this.protectionType = protectionType;
		this.squareColor = squareColor;
		this.positionSquare = positionSquare;
		this.isNew = isNew;
		this.zone = zone;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getExtinguisherType() {
		return extinguisherType;
	}

	public void setExtinguisherType(String extinguisherType) {
		this.extinguisherType = extinguisherType;
	}

	public String getProtectionType() {
		return protectionType;
	}

	public void setProtectionType(String protectionType) {
		this.protectionType = protectionType;
	}

	public Color getSquareColore() {
		return squareColor;
	}

	public void setSquareColore(Color squareColore) {
		this.squareColor = squareColore;
	}

	public Point getPositionSquare() {
		return positionSquare;
	}

	public void setPositionSquare(Point positionSquare) {
		this.positionSquare = positionSquare;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}
