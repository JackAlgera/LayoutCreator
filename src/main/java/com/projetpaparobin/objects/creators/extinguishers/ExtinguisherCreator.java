package com.projetpaparobin.objects.creators.extinguishers;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.extinguishers.ExtinguisherID;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

public class ExtinguisherCreator {

	private LayoutHandler layoutHandler;
	private static int nbrInstance = 1;

	private EExtinguisherCreationState state;
	private ArrayList<IExtinguisherCreatorListener> listeners;
	private Zone selectedZone;
	private Extinguisher currentExtinguisher;

	public ExtinguisherCreator(LayoutHandler layoutHandler) {
		this.layoutHandler = layoutHandler;
		state = EExtinguisherCreationState.FINISHED;
		listeners = new ArrayList<IExtinguisherCreatorListener>();
	}
	
	public Extinguisher getCurrentExtinguisher() {
		return currentExtinguisher;
	}

	public void newExtinguisher() {
		currentExtinguisher = new Extinguisher();
		state = EExtinguisherCreationState.NEW_EXTINGUISHER;
		sendEvent(EExtinguisherEvents.CREATING_NEW_EXTINGUISHER);
	}

	public void setPosition(Point pos) {
		if (state == EExtinguisherCreationState.NEW_EXTINGUISHER) {
			currentExtinguisher.setExtinguisherPos(pos);
		}
	}

	public void setZone(Zone zone) {
		if (zone != null && state == EExtinguisherCreationState.NEW_EXTINGUISHER) {
			selectedZone = zone;
			currentExtinguisher.setZone(zone);
			state = EExtinguisherCreationState.SETTING_NAME;
			sendEvent(EExtinguisherEvents.SETTING_NAME);
		}
	}

	public void setExtinguisherID(ExtinguisherID id) {
		if (currentExtinguisher != null && id != null && (state == EExtinguisherCreationState.SETTING_NAME)) {
			currentExtinguisher.addID(id);
			doneCreatingExtinguisher();
		}
	}

	private void doneCreatingExtinguisher() {
		if (state == EExtinguisherCreationState.SETTING_NAME) {
			state = EExtinguisherCreationState.FINISHED;
			selectedZone.addExtinguisher(currentExtinguisher);
			layoutHandler.addExtinguisher(currentExtinguisher);
			nbrInstance++;
			sendEvent(EExtinguisherEvents.FINISHED_CREATING_EXTINGUISHER);
		}
	}

	public static String getDefaultZoneNumber() {
		if (nbrInstance < 10) {
			return "0" + nbrInstance;
		} else {
			return "" + nbrInstance;
		}
	}

	public void canceled() {
		state = EExtinguisherCreationState.FINISHED;
		sendEvent(EExtinguisherEvents.CANCELED);
	}

	public void reset() {
		nbrInstance = 1;
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
