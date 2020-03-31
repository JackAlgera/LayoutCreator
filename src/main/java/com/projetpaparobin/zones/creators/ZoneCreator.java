package com.projetpaparobin.zones.creators;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.zones.Point;
import com.projetpaparobin.zones.Zone;

public class ZoneCreator {

	private static ZoneCreator instance;
	
	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private EZoneStage shapeState;
	private ArrayList<IZoneCreatorListener> listeners;
	private Zone currentZone;
	
	private ZoneCreator() {
		shapeState = EZoneStage.NEW_ZONE;
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
		shapeState = EZoneStage.ADDING_POINTS;
		sendEvent(EZoneEvents.CREATING_NEW_ZONE);
	}
	
	public void addPoint(Point p) {
		if(shapeState == EZoneStage.ADDING_POINTS) {
			currentZone.getShape().addPoint(p);
			sendEvent(EZoneEvents.ADDED_POINT);
		}
	}
	
	public void finishedCreatingShape() {
		if(shapeState == EZoneStage.ADDING_POINTS) {
			sendEvent(EZoneEvents.SETTING_NAME);
		}
	}

	public void doneCreatingZone() {
		if(shapeState == EZoneStage.SETTING_NAME) {
			shapeState = EZoneStage.FINISHED;
			layoutHandler.addZone(currentZone);
			sendEvent(EZoneEvents.FINISHED_CREATING_ZONE);
		}
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
