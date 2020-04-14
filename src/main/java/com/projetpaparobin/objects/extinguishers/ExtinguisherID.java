package com.projetpaparobin.objects.extinguishers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpaparobin.utils.UIColor;

public class ExtinguisherID {

	private String number; //from 01 to XX 
	private String extinguisherType;
	private EProtectionType protectionType; 
	private int fabricationYear;
	private String brand;
	private boolean isNew;
	private UIColor color;
		
	public ExtinguisherID() {
	}
	
	public ExtinguisherID(String number, String extinguisherType, EProtectionType protectionType, int fabricationYear,
			String brand, boolean isNew, UIColor color) {
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

	public String getExtinguisherType() {
		return extinguisherType;
	}

	public void setExtinguisherType(String extinguisherType) {
		this.extinguisherType = extinguisherType;
	}

	public EProtectionType getProtectionType() {
		return protectionType;
	}

	public void setProtectionType(EProtectionType protectionType) {
		this.protectionType = protectionType;
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

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public UIColor getColor() {
		return color;
	}

	public void setColor(UIColor color) {
		this.color = color;
	}

	@JsonIgnore
	public String getDisplayText() {
		return extinguisherType + "/" + protectionType;
	}
	
}
