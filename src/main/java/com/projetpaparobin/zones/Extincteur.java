package com.projetpaparobin.zones;

public class Extincteur {

	
	private int number; //from 1 to n 
	private String extinguisherType;
	private String protectionType; 
	private String squareColor; //either blue,red or yellow
	private Point positionSquare;
	private boolean isNew;
	
	public Extincteur(int number, String extinguisherType, String protectionType, String squareColore,
			Point positionSquare, boolean isNew) {
		this.number = number;
		this.extinguisherType = extinguisherType;
		this.protectionType = protectionType;
		this.squareColore = squareColore;
		this.positionSquare = positionSquare;
		this.isNew = isNew;
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

	public String getSquareColore() {
		return squareColore;
	}

	public void setSquareColore(String squareColore) {
		this.squareColore = squareColore;
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
