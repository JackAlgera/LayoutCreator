package com.projetpaparobin.objects.extinguishers;

import javafx.scene.paint.Color;

public class ExtinguisherID {

	private int number; //from 01 to XX 
	private String extinguisherType;
	private String protectionType; 
	private int fabricationYear;
	private String brand;
	private boolean isNew;
	private Color color; //either blue,red or yellow
		
	public ExtinguisherID(int number, String extinguisherType, String protectionType, int fabricationYear,
			String brand, boolean isNew, Color color) {
		super();
		this.number = number;
		this.extinguisherType = extinguisherType;
		this.protectionType = protectionType;
		this.fabricationYear = fabricationYear;
		this.brand = brand;
		this.isNew = isNew;
		this.color = color;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}	

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public int getFabricationYear() {
		return fabricationYear;
	}

	public void setFabricationYear(int fabricationYear) {
		this.fabricationYear = fabricationYear;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getDisplayText() {
		return extinguisherType + "/" + protectionType;
	}
	
}
