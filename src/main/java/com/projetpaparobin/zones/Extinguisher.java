package com.projetpaparobin.zones;

import java.awt.Color;

public class Extinguisher {

	private int number; //from 1 to n 
	private String extinguisherType;
	private String protectionType; 
	private int anneeMiseEnService;
	private String marque;
	private Color squareColor; //either blue,red or yellow
	private Point positionSquare;
	private boolean isNew;
	private Zone zone;
	
	public Extinguisher(int number, String extinguisherType, String protectionType, Color squareColor,
			Point positionSquare, boolean isNew, Zone zone,int anneeMiseEnService,String marque) {
		this.number = number;
		this.extinguisherType = extinguisherType;
		this.protectionType = protectionType;
		this.squareColor = squareColor;
		this.positionSquare = positionSquare;
		this.isNew = isNew;
		this.zone = zone;
		this.anneeMiseEnService = anneeMiseEnService;
		this.marque = marque;
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

	public int getAnneeMiseEnService() {
		return anneeMiseEnService;
	}

	public void setAnneeMiseEnService(int anneeMiseEnService) {
		this.anneeMiseEnService = anneeMiseEnService;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	
}
