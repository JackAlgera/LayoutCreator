package com.projetpaparobin.objects.creators.zones;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.zones.ZoneID;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

public class ZoneCreator {

	private static int nbrInstance = 1;

	private LayoutHandler layoutHandler;
	private EZoneCreationState zoneState;
	private ArrayList<IZoneCreatorListener> listeners;
	private Zone currentZone;

	public ZoneCreator(LayoutHandler layoutHandler) {
		this.layoutHandler = layoutHandler;
		zoneState = EZoneCreationState.NEW_ZONE;
		listeners = new ArrayList<IZoneCreatorListener>();
		currentZone = null;
	}

	public Zone getCurrentZone() {
		return currentZone;
	}

	public void newZone() {
		currentZone = new Zone();
		zoneState = EZoneCreationState.ADDING_POINTS;
		sendEvent(EZoneEvents.CREATING_NEW_ZONE);
	}

	public void addPoint(Point p) {
		if (zoneState == EZoneCreationState.ADDING_POINTS) {
			currentZone.getShape().addPoint(p);
			sendEvent(EZoneEvents.ADDED_POINT);
		}
	}

	public void finishedCreatingShape() {
		if (zoneState == EZoneCreationState.ADDING_POINTS) {
			if (!currentZone.getShape().isEmpty()) {
				zoneState = EZoneCreationState.SETTING_NAME;
				sendEvent(EZoneEvents.SETTING_NAME);
			} else {
				canceled();
			}
		}
	}

	public void setZoneID(ZoneID id) {
		if (currentZone != null && (zoneState == EZoneCreationState.SETTING_NAME)) {
			currentZone.setId(id);
			doneCreatingZone();
		}
	}

	private void doneCreatingZone() {
		if (zoneState == EZoneCreationState.SETTING_NAME) {
			zoneState = EZoneCreationState.FINISHED;
			layoutHandler.addZone(currentZone);
			nbrInstance++;
			sendEvent(EZoneEvents.FINISHED_CREATING_ZONE);
		}
	}

	public void canceled() {
		zoneState = EZoneCreationState.FINISHED;
		sendEvent(EZoneEvents.CANCELED);
	}

	public static void setNbrInstances(int nbrInstsances) {
		ZoneCreator.nbrInstance = nbrInstsances;
	}
	
	public int getDefaultZoneNumber() {
		return nbrInstance;
	}

	public void addListener(IZoneCreatorListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IZoneCreatorListener listener) {
		listeners.remove(listener);
	}

	private void sendEvent(EZoneEvents event) {
		for (IZoneCreatorListener listener : listeners) {
			listener.handleZoneCreatorEvent(event);
		}
	}

}
