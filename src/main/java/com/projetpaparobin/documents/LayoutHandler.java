package com.projetpaparobin.documents;

import java.util.ArrayList;

import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LayoutHandler {

	private static LayoutHandler instance;
	private ObservableList<Zone> zones;
	private ObservableList<Extinguisher> extinguishers;
	
	private LayoutHandler() {		
		zones = FXCollections.observableArrayList();
		extinguishers = FXCollections.observableArrayList();
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
	
	public void removeZone(Zone zone) {
		zones.remove(zone);
	}
	
	public void setZones(ArrayList<Zone> zones) {
		this.zones.clear();
		for (Zone zone : zones) {
			addZone(zone);
		}
	}
	
	public ObservableList<Zone> getZones() {
		return zones;
	}
	
	public void addExtinguisher(Extinguisher ex) {
		extinguishers.add(ex);
	}
	
	public void setExtinguishers(ArrayList<Extinguisher> extinguishers) {
		this.extinguishers.clear();
		for (Extinguisher ex : extinguishers) {
			addExtinguisher(ex);
		}
	}
	
	public ObservableList<Extinguisher> getExtinguishers() {
		return extinguishers;
	}
		
	public void fullReset() {
		zones.clear();
		extinguishers.clear();
		ZoneCreator.getInstance().reset();
		ExtinguisherCreator.getInstance().reset();
		
		UIZoneHandler.getInstance().reset();
		UIExtinguisherHandler.getInstance().reset();
		UITextHandler.getInstance().reset();
		
		UIElements.colorIndex = 1;
	}
	
}
