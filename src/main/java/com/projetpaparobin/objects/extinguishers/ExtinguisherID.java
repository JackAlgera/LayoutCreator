package com.projetpaparobin.objects.extinguishers;

import javafx.scene.paint.Color;

public class ExtinguisherID {

	private int number; //from 1 to n 
	private String extinguisherType;
	private String protectionType; 
	private int anneeMiseEnService;
	private String marque;
	private boolean isNew;
	private Color squareColor; //either blue,red or yellow
		
	public ExtinguisherID(int number, String extinguisherType, String protectionType, int anneeMiseEnService,
			String marque, boolean isNew, Color squareColor) {
		super();
		this.number = number;
		this.extinguisherType = extinguisherType;
		this.protectionType = protectionType;
		this.anneeMiseEnService = anneeMiseEnService;
		this.marque = marque;
		this.isNew = isNew;
		this.squareColor = squareColor;
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
	
	public String getDisplayText() {
		return extinguisherType + "/" + protectionType;
	}
	
}
