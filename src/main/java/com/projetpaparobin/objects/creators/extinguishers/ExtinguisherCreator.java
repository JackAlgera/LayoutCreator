package com.projetpaparobin.objects.creators.extinguishers;

import java.util.ArrayList;

import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.extinguishers.ExtinguisherID;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

public class ExtinguisherCreator {
	
	private static ExtinguisherCreator instance;
	private static int nbrInstance = 1;
	
	private EExtinguisherCreationState state;
	private ArrayList<IExtinguisherCreatorListener> listeners;
	private Zone selectedZone;
	private Extinguisher currentExtinguisher;
	
	private ExtinguisherCreator() {
		state = EExtinguisherCreationState.FINISHED;
		selectedZone = new Zone();
		currentExtinguisher = new Extinguisher();
		listeners = new ArrayList<IExtinguisherCreatorListener>();
	}
	
	public static ExtinguisherCreator getInstance() {
		if(instance == null) {
			instance = new ExtinguisherCreator();
		}
		
		return instance;
	}
	
	public Extinguisher getCurrentExtinguisher() {
		return currentExtinguisher;
	}
		
	public void newExtinguisher() {
		currentExtinguisher = new Extinguisher();
		state = EExtinguisherCreationState.NEW_EXTINGUISHER;
		sendEvent(EExtinguisherEvents.CREATING_NEW_EXTINGUISHER);
	}
	
	public void setPosition(Point p) {
		if(state == EExtinguisherCreationState.NEW_EXTINGUISHER) {
			currentExtinguisher.setPos(p);
		}
	}
	
	public void setZone(Zone zone) {
		if(zone != null && state == EExtinguisherCreationState.NEW_EXTINGUISHER) {
			selectedZone = zone;
			currentExtinguisher.setZone(zone);
			state =EExtinguisherCreationState.SETTING_NAME; 
			sendEvent(EExtinguisherEvents.SETTING_NAME);
		}
	}
	
	public void setExtinguisherID(ExtinguisherID id) {
		if(currentExtinguisher != null && (state == EExtinguisherCreationState.SETTING_NAME)) {
			currentExtinguisher.setId(id);
			doneCreatingExtinguisher();
		}
	}
	
	private void doneCreatingExtinguisher() {
		if(state == EExtinguisherCreationState.SETTING_NAME) {
			state = EExtinguisherCreationState.FINISHED;
			selectedZone.addExtinguisher(currentExtinguisher);
			nbrInstance++;
			sendEvent(EExtinguisherEvents.FINISHED_CREATING_EXTINGUISHER);
		}
	}
	
	public static String getDefaultZoneNumber() {
		if(nbrInstance < 10) {
			return "0" + nbrInstance;
		} else {
			return "" + nbrInstance;
		}
	}
	
	public void canceled() {
		state = EExtinguisherCreationState.FINISHED;
		sendEvent(EExtinguisherEvents.CANCELED);
	}
	
	public void addListener(IExtinguisherCreatorListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IExtinguisherCreatorListener listener) {
		listeners.remove(listener);
	}

	private void sendEvent(EExtinguisherEvents event) {
		for (IExtinguisherCreatorListener listener : listeners) {
			listener.handleExtinguisherCreatorEvent(event);
		}
	}
	
}
