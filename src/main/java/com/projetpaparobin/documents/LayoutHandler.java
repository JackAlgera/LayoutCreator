package com.projetpaparobin.documents;

import java.util.ArrayList;

import com.projetpaparobin.zones.Zone;

public class LayoutHandler {

	private static LayoutHandler instance;
	private ArrayList<Zone> zones;
	
	private LayoutHandler() {		
		zones = new ArrayList<Zone>();
	}
	
	public static LayoutHandler getInstance() {
		if(instance == null) {
			instance = new LayoutHandler();
		}
		
		return instance;
	}
	
	public void addZone(Zone newZone) {
		zones.add(newZone);
	}	

	public ArrayList<Zone> getZones() {
		return zones;
	}
	
	public Zone getZone(double posX, double posY) {
		for (Zone zone : zones) {
			if(zone.containsPoint(posX, posY)) {
				return zone;
			}
		}
		
		return null;
	}
}
