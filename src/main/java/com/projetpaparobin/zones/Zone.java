package com.projetpaparobin.zones;

import java.util.ArrayList;

public class Zone {
	
	private Identifiant identifiant;
	private Shape shape;
	private String color;
	private ArrayList<Extincteur> extincteurs;	
	
	public Zone(Identifiant identifiant, Shape shape, String color, ArrayList<Extincteur> extincteurs) {
		this.identifiant = identifiant;
		this.shape = shape;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ArrayList<Extincteur> getExtincteurs() {
		return extincteurs;
	}

	public void setExtincteurs(ArrayList<Extincteur> extincteurs) {
		this.extincteurs = extincteurs;
	}
		
}
