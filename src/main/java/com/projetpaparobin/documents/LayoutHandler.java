package com.projetpaparobin.documents;

import java.util.ArrayList;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

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

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}
	
	public ArrayList<Zone> getZones() {
		return zones;
	}
	
	public void fullReset() {
		zones.clear();
		ZoneCreator.getInstance().reset();
		ExtinguisherCreator.getInstance().reset();
		
		UIZoneHandler.getInstance().reset();
		UIExtinguisherHandler.getInstance().reset();
		UITextHandler.getInstance().reset();
		
		UIElements.colorIndex = 1;
	}
	
}
