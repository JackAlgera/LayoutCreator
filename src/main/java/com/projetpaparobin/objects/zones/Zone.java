package com.projetpaparobin.objects.zones;

import java.util.ArrayList;

import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.paint.Color;

public class Zone {
	
	private ZoneID id;
	private Shape shape;
	private Color rimColor, fillColor;
	private ArrayList<Extinguisher> extinguishers;	
	
	public Zone() {
		this.shape = new Shape();
		this.extinguishers = new ArrayList<Extinguisher>();
		this.fillColor = UIElements.getRandomColor();
		this.rimColor = fillColor.darker();
	}
	
	public Zone(ZoneID id, Shape shape, Color rimColor, Color fillColor, ArrayList<Extinguisher> extinguishers) {
		this.id = id;
		this.shape = shape;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.extinguishers = extinguishers;
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

	public Color getRimColor() {
		return rimColor;
	}

	public void setRimColor(Color rimColor) {
		this.rimColor = rimColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
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

	public String getDisplayText() {
		return id.getDisplayText();
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
