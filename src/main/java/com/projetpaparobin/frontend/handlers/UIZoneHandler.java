package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UICorner;
import com.projetpaparobin.frontend.elements.UIZone;

public class UIZoneHandler {
	
	private static UIZoneHandler instance;
	private ArrayList<UIZone> shapes;
		
	private UIZoneHandler() {	
		shapes = new ArrayList<UIZone>();
	}
	
	public static UIZoneHandler getInstance() {
		if(instance == null) {
			instance = new UIZoneHandler();
		}
		
		return instance;
	}
	
	public ArrayList<UIZone> getZones() {
		return shapes;
	}
	
	public void add(UIZone zone) {
		shapes.add(zone);
	}
	
	public UIZone getZone(double posX, double posY) {
		for (UIZone zone : shapes) {
			if(zone.containsPoint(posX, posY)) {
				return zone;
			}
		}
		return null;
	}
	
	public UICorner getCorner(double posX, double posY) {
		for (UIZone zone : shapes) {
			UICorner corner = zone.getCorner(posX, posY);
			if(corner != null) {
				return corner;
			}
		}
		return null;
	}
}
