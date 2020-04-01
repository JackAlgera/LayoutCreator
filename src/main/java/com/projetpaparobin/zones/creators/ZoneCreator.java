package com.projetpaparobin.zones.creators;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.zones.Identifiant;
import com.projetpaparobin.zones.Point;
import com.projetpaparobin.zones.Zone;

public class ZoneCreator {

	private static ZoneCreator instance;
	private static int nbrInstance = 0;
	
	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private EZoneStage zoneState;
	private ArrayList<IZoneCreatorListener> listeners;
	private Zone currentZone;
	
	private ZoneCreator() {
		zoneState = EZoneStage.NEW_ZONE;
		currentZone = new Zone();
		listeners = new ArrayList<IZoneCreatorListener>();
	}
	
	public static ZoneCreator getInstance() {
		if(instance == null) {
			instance = new ZoneCreator();
		}
		
		return instance;
	}
	
	public Zone getCurrentZone() {
		return currentZone;
	}
		
	public void newZone() {
		currentZone = new Zone();
		zoneState = EZoneStage.ADDING_POINTS;
		sendEvent(EZoneEvents.CREATING_NEW_ZONE);
	}
	
	public void addPoint(Point p) {
		if(zoneState == EZoneStage.ADDING_POINTS) {
			currentZone.getShape().addPoint(p);
			sendEvent(EZoneEvents.ADDED_POINT);
		}
	}
	
	public void finishedCreatingShape() {
		if(zoneState == EZoneStage.ADDING_POINTS) {
			zoneState = EZoneStage.SETTING_NAME;
			sendEvent(EZoneEvents.SETTING_NAME);
		}
	}

	public void setZoneIdentifiant(String areaType, String activityType, int areaSizes) {
		if(currentZone != null && (zoneState == EZoneStage.SETTING_NAME)) {
			currentZone.setIdentifiant(new Identifiant(areaType, activityType, areaSizes));
			doneCreatingZone();
		}
	}
	
	private void doneCreatingZone() {
		if(zoneState == EZoneStage.SETTING_NAME) {
			zoneState = EZoneStage.FINISHED;
			layoutHandler.addZone(currentZone);
			sendEvent(EZoneEvents.FINISHED_CREATING_ZONE);
		}
	}
	
	public void canceled() {
		zoneState = EZoneStage.FINISHED;
		sendEvent(EZoneEvents.CANCELED);
	}
	
	public void addListener(IZoneCreatorListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IZoneCreatorListener listener) {
		listeners.remove(listener);
	}

	private void sendEvent(EZoneEvents event) {
		for (IZoneCreatorListener listener : listeners) {
			listener.handleEvent(event);
		}
	}
		
}
