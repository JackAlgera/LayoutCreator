package com.projetpaparobin.objects.extinguishers;

import javafx.scene.paint.Color;

public class ExtinguisherID {

	private String number; //from 01 to XX 
	private EExtinguisherType extinguisherType;
	private EProtectionType protectionType; 
	private int fabricationYear;
	private String brand;
	private boolean isNew;
	private Color color; //either blue,red or yellow
		
	public ExtinguisherID(String number, EExtinguisherType extinguisherType, EProtectionType protectionType, int fabricationYear,
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public EExtinguisherType getExtinguisherType() {
		return extinguisherType;
	}

	public void setExtinguisherType(EExtinguisherType extinguisherType) {
		this.extinguisherType = extinguisherType;
	}

	public EProtectionType getProtectionType() {
		return protectionType;
	}

	public void setProtectionType(EProtectionType protectionType) {
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
