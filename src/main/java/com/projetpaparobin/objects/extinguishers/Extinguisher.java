package com.projetpaparobin.objects.extinguishers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;

public class Extinguisher {

	private ExtinguisherID id;
	private Point pos, textAreaPosition;
	private double textAreaSize;
	
	@JsonBackReference
	private Zone zone;
	
	public Extinguisher() {
	}
	
	public Extinguisher(String number, String extinguisherType, EProtectionType protectionType, UIColor color,
			Point pos, boolean isNew, Zone zone,int anneeMiseEnService,String marque) {
		id = new ExtinguisherID(number, extinguisherType, protectionType, anneeMiseEnService, marque, isNew, color);
		this.pos = pos;
		this.zone = zone;
		this.textAreaSize = 0;
	}
	
	public double getTextAreaSize() {
		return textAreaSize;
	}

	public void setTextAreaSize(double textAreaSize) {
		this.textAreaSize = textAreaSize;
	}
	
	public Point getTextAreaPosition() {
		return textAreaPosition;
	}

	public void setTextAreaPosition(Point textAreaPosition) {
		this.textAreaPosition = textAreaPosition;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	@JsonIgnore
	public String getDisplayText() {
		return id.getDisplayText();
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public void setId(ExtinguisherID id) {
		this.id = id;
	}
	
	public ExtinguisherID getId() {
		return id;
	}

	@JsonIgnore
	public String getNumber() {
		return id.getNumber();
	}
	@JsonIgnore
	public String getExtinguisherType() {
		return id.getExtinguisherType();
	}
	@JsonIgnore
	public EProtectionType getProtectionType() {
		return id.getProtectionType();
	}
	@JsonIgnore
	public int getFabricationYear() {
		return id.getFabricationYear();
	}
	@JsonIgnore
	public String getBrand() {
		return id.getBrand();
	}
	@JsonIgnore
	public boolean getIsNew() {
		return id.isNew();
	}
	@JsonIgnore
	public String getFillColor() {
		return id.getColor().getColorName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
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
		Extinguisher other = (Extinguisher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}
	
}
