package com.projetpaparobin.objects.zones;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.paint.Color;

public class Zone {
	
	private ZoneID id;
	private Shape shape;
	private UIColor fillColor;
	@JsonIgnore
	private Color rimColor;
	private Point textAreaPosition;
	
	@JsonManagedReference
	private ArrayList<Extinguisher> extinguishers;	
	
	public Zone() {
		this.shape = new Shape();
		this.extinguishers = new ArrayList<Extinguisher>();
		this.fillColor = UIElements.getRandomColor();
		this.rimColor = fillColor.getColor().darker();
		this.textAreaPosition = null;
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

	public UIColor getFillColor() {
		return fillColor;
	}
	
	public Color getRimColor() {
		return rimColor;
	}
	
	public void setFillColor(UIColor fillColor) {
		if(fillColor == null) {
			this.fillColor = UIElements.getRandomColor();
		} else {
			this.fillColor = fillColor;
		}
		this.rimColor = fillColor.getColor().darker();
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
		return shape.getArea().contains(posX, posY);
	}

	public Point getTextAreaPosition() {
		return textAreaPosition;
	}

	public void setTextAreaPosition(Point textAreaPosition) {
		this.textAreaPosition = textAreaPosition;
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
		result = prime * result + ((fillColor == null) ? 0 : fillColor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rimColor == null) ? 0 : rimColor.hashCode());
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
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
		if (fillColor == null) {
			if (other.fillColor != null)
				return false;
		} else if (!fillColor.equals(other.fillColor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rimColor == null) {
			if (other.rimColor != null)
				return false;
		} else if (!rimColor.equals(other.rimColor))
			return false;
		if (shape == null) {
			if (other.shape != null)
				return false;
		} else if (!shape.equals(other.shape))
			return false;
		return true;
	}
	
}
