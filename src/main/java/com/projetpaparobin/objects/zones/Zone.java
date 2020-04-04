package com.projetpaparobin.objects.zones;

import java.util.ArrayList;

import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.paint.Color;

public class Zone {
	
	private IdentifiantZone identifiant;
	private Shape shape;
	private Color rimColor, fillColor;
	private ArrayList<Extinguisher> extincteurs;	
	
	public Zone() {
		this.shape = new Shape();
		this.extincteurs = new ArrayList<Extinguisher>();
		this.fillColor = UIElements.getRandomColor();
		this.rimColor = fillColor.darker();
	}
	
	public Zone(IdentifiantZone identifiant, Shape shape, Color rimColor, Color fillColor, ArrayList<Extinguisher> extincteurs) {
		this.identifiant = identifiant;
		this.shape = shape;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.extincteurs = extincteurs;
	}

	public IdentifiantZone getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(IdentifiantZone identifiant) {
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

	public ArrayList<Extinguisher> getExtincteurs() {
		return extincteurs;
	}

	public void setExtincteurs(ArrayList<Extinguisher> extincteurs) {
		this.extincteurs = extincteurs;
	}
	
	public boolean containsPoint(double posX, double posY) {
		return shape.getArea().contains(posX, posY);
	}
		
	public String getDisplayText() {
		return identifiant.getAreaType() + identifiant.getAreaNumber() + "-i-" + identifiant.getAreaSize() + "m2";
	}
	
}
