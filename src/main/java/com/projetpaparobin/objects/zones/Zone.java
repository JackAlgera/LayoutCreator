package com.projetpaparobin.objects.zones;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIColor;

import javafx.scene.paint.Color;

public class Zone {

	private ZoneID id;
	private Shape shape;
	private Point textAreaPos;
	private double textAreaSize;
	private Point textConnectionCenterPos;

	@JsonManagedReference
	private ArrayList<Extinguisher> extinguishers;

	public Zone() {
		this.shape = new Shape();
		this.extinguishers = new ArrayList<Extinguisher>();
		this.textAreaPos = null;
		this.textConnectionCenterPos = null;
		this.textAreaSize = 0;
		this.id = new ZoneID();
	}

	@JsonIgnore
	public UIColor getFillColor() {
		return id.getFillColor();
	}

	@JsonIgnore
	public void setFillColor(UIColor fillColor) {
		id.setFillColor(fillColor);
	}

	@JsonIgnore
	public Color getRimColor() {
		return id.getRimColor();
	}

	public ZoneID getId() {
		return id;
	}

	public void setId(ZoneID id) {
		this.id = id;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void removeExtinguisher(Extinguisher ex) {
		extinguishers.remove(ex);
	}

	public void addExtinguisher(Extinguisher ex) {
		extinguishers.add(ex);
	}

	public ArrayList<Extinguisher> getExtinguishers() {
		return extinguishers;
	}

	public void setExtinguishers(ArrayList<Extinguisher> extinguishers) {
		this.extinguishers = extinguishers;
	}

	public boolean containsPoint(double posX, double posY) {
		return shape.containsPoint(posX, posY);
	}

	public Point getTextAreaPos() {
		return textAreaPos;
	}

	public void setTextAreaPos(Point textAreaPos) {
		this.textAreaPos = textAreaPos;
	}

	public Point getTextConnectionCenterPos() {
		return textConnectionCenterPos;
	}

	public void setTextConnectionCenterPos(Point textConnectionCenterPos) {
		this.textConnectionCenterPos = textConnectionCenterPos;
	}

	public double getTextAreaSize() {
		return textAreaSize;
	}

	public void setTextAreaSize(double textAreaSize) {
		this.textAreaSize = textAreaSize;
	}

	@JsonIgnore
	public String getDisplayText() {
		return id.getDisplayText();
	}

	@JsonIgnore
	public String getAreaName() {
		return id.getAreaName();
	}

	@JsonIgnore
	public int getAreaNumber() {
		return id.getAreaNumber();
	}

	@JsonIgnore
	public String getAreaType() {
		return id.getAreaType().toString();
	}

	@JsonIgnore
	public String getActivityType() {
		return id.getActivityType().toString();
	}

	@JsonIgnore
	public int getAreaSize() {
		return id.getAreaSize();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extinguishers == null) ? 0 : extinguishers.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
		result = prime * result + ((textAreaPos == null) ? 0 : textAreaPos.hashCode());
		long temp;
		temp = Double.doubleToLongBits(textAreaSize);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((textConnectionCenterPos == null) ? 0 : textConnectionCenterPos.hashCode());
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
		Zone other = (Zone) obj;
		if (extinguishers == null) {
			if (other.extinguishers != null)
				return false;
		} else if (!extinguishers.equals(other.extinguishers))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (shape == null) {
			if (other.shape != null)
				return false;
		} else if (!shape.equals(other.shape))
			return false;
		if (textAreaPos == null) {
			if (other.textAreaPos != null)
				return false;
		} else if (!textAreaPos.equals(other.textAreaPos))
			return false;
		if (Double.doubleToLongBits(textAreaSize) != Double.doubleToLongBits(other.textAreaSize))
			return false;
		if (textConnectionCenterPos == null) {
			if (other.textConnectionCenterPos != null)
				return false;
		} else if (!textConnectionCenterPos.equals(other.textConnectionCenterPos))
			return false;
		return true;
	}

}
