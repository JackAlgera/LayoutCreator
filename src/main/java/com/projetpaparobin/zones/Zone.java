package com.projetpaparobin.zones;

import java.util.ArrayList;

import com.projetpaparobin.utils.UIElements;

import javafx.scene.paint.Color;

public class Zone {
	
	private Identifiant identifiant;
	private Shape shape;
	private Color rimColor, fillColor;
	private ArrayList<Extincteur> extincteurs;	
	
	public Zone() {
		this.shape = new Shape();
		this.extincteurs = new ArrayList<Extincteur>();
		this.fillColor = UIElements.getRandomColor();
		this.rimColor = fillColor.darker();
	}
	
	public Zone(Identifiant identifiant, Shape shape, Color rimColor, Color fillColor, ArrayList<Extincteur> extincteurs) {
		this.identifiant = identifiant;
		this.shape = shape;
		this.rimColor = rimColor;
		this.fillColor = fillColor;
		this.extincteurs = extincteurs;
	}

	public Identifiant getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(Identifiant identifiant) {
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

	public ArrayList<Extincteur> getExtincteurs() {
		return extincteurs;
	}

	public void setExtincteurs(ArrayList<Extincteur> extincteurs) {
		this.extincteurs = extincteurs;
	}
		
}
