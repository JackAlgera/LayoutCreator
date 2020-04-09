package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UICorner;
import com.projetpaparobin.frontend.elements.UIZone;

public class UIZoneHandler {
	
	private static UIZoneHandler instance;
	private ArrayList<UIZone> zones;
		
	private UIZoneHandler() {	
		zones = new ArrayList<UIZone>();
	}
	
	public static UIZoneHandler getInstance() {
		if(instance == null) {
			instance = new UIZoneHandler();
		}
		
		return instance;
	}
	
	public ArrayList<UIZone> getZones() {
		return zones;
	}
	
	public void add(UIZone zone) {
		zones.add(zone);
	}
	
	public UIZone getZone(double posX, double posY) {
		boolean foundZone = false;
		UIZone theZone = null;
		
		for (UIZone zone : zones) {
			if(!foundZone && zone.containsPoint(posX, posY)) {
				theZone = zone;
				foundZone = true;
				theZone.setShouldDrawCorners(true);
			} else {
				zone.setShouldDrawCorners(false);
			}
		}
		
		return theZone;
	}
	
	public void removeSelectedZone() {
		for (UIZone zone : zones) {
			zone.setShouldDrawCorners(false);
		}
	}
	
	public UICorner getCorner(double posX, double posY) {
		for (UIZone zone : zones) {
			if(zone.shouldDrawCorners()) {
				UICorner corner = zone.getCorner(posX, posY);
				if(corner != null) {
					return corner;
				}
			}
		}
		return null;
	}
	
	public void reset() {
		zones.clear();
	}
	
}
