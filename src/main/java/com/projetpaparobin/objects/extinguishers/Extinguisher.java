package com.projetpaparobin.objects.extinguishers;

import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

import javafx.scene.paint.Color;

public class Extinguisher {

	private ExtinguisherID id;
	private Point positionSquare;
	private Zone zone;
	
	public Extinguisher(int number, String extinguisherType, String protectionType, Color squareColor,
			Point positionSquare, boolean isNew, Zone zone,int anneeMiseEnService,String marque) {
		id = new ExtinguisherID(number, extinguisherType, protectionType, anneeMiseEnService, marque, isNew, squareColor);
		this.positionSquare = positionSquare;
		this.zone = zone;
	}

	public ExtinguisherID getId() {
		return id;
	}
	
	public Point getPositionSquare() {
		return positionSquare;
	}

	public void setPositionSquare(Point positionSquare) {
		this.positionSquare = positionSquare;
	}

	public String getDisplayText() {
		return id.getDisplayText();
	}
	
}
