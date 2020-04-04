package com.projetpaparobin.objects.zones;

import java.util.ArrayList;

import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.paint.Color;

public class Zone {
	
	private ZoneID identifiant;
	private Shape shape;
	private Color rimColor, fillColor;
	private ArrayList<Extinguisher> extinguishers;	
	
	public Zone() {
		this.shape = new Shape();
		this.extinguishers = new ArrayList<Extinguisher>();
		this.fillColor = UIElements.getRandomColor();
		this.rimColor = fillColor.darker();
	}
	
	public Zone(ZoneID identifiant, Shape shape, Color rimColor, Color fillColor, ArrayList<Extinguisher> extinguishers) {
		this.identifiant = identifiant;
		this.shape = shape;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.extinguishers = extinguishers;
	}

	public ZoneID getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(ZoneID identifiant) {
		this.identifiant = identifiant;
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
		return identifiant.getAreaType() + identifiant.getAreaNumber() + "-i-" + identifiant.getAreaSize() + "m2";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extinguishers == null) ? 0 : extinguishers.hashCode());
		result = prime * result + ((fillColor == null) ? 0 : fillColor.hashCode());
		result = prime * result + ((identifiant == null) ? 0 : identifiant.hashCode());
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
		if (identifiant == null) {
			if (other.identifiant != null)
				return false;
		} else if (!identifiant.equals(other.identifiant))
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
