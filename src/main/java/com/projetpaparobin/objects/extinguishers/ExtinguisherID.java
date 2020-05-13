package com.projetpaparobin.objects.extinguishers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpaparobin.utils.UIColor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ExtinguisherID {

	private String number;
	private String extinguisherType;
	private String protectionType;
	private int fabricationYear;
	private String brand;
	private UIColor color;
	private String local;
	private Boolean isNew;

	public ExtinguisherID() {
	}

	public ExtinguisherID(String number, String extinguisherType, String protectionType, int fabricationYear,
			String brand, Boolean isNew, UIColor color, String local) {
		this.isNew = isNew;
		this.number = number;
		this.extinguisherType = extinguisherType;
		this.protectionType = protectionType;
		this.fabricationYear = fabricationYear;
		this.brand = brand;
		this.color = color;
		this.local = local;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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

	public String getProtectionType() {
		return protectionType;
	}

	public void setProtectionType(String protectionType) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((extinguisherType == null) ? 0 : extinguisherType.hashCode());
		result = prime * result + fabricationYear;
		result = prime * result + ((isNew == null) ? 0 : isNew.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((protectionType == null) ? 0 : protectionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtinguisherID other = (ExtinguisherID) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (color != other.color)
			return false;
		if (extinguisherType == null) {
			if (other.extinguisherType != null)
				return false;
		} else if (!extinguisherType.equals(other.extinguisherType))
			return false;
		if (fabricationYear != other.fabricationYear)
			return false;
		if (isNew == null) {
			if (other.isNew != null)
				return false;
		} else if (!isNew.equals(other.isNew))
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (protectionType == null) {
			if (other.protectionType != null)
				return false;
		} else if (!protectionType.equals(other.protectionType))
			return false;
		return true;
	}
	

}
