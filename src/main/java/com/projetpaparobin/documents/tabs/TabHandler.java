package com.projetpaparobin.documents.tabs;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.Comment;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TabHandler implements ITabHandler {

	private static TabHandler instance;
	
	private ObservableList<LayoutHandler> layoutHandlers;
	private LayoutHandler selectedLayoutHandler;
	
	private ObservableList<Zone> zones;
	private ObservableList<Extinguisher> extinguishers;
	private ObservableList<Comment> comments;
	
	private ArrayList<ITabHandler> listeners;
	
	private TabHandler() {		
		zones = FXCollections.observableArrayList();
		extinguishers = FXCollections.observableArrayList();		
		comments = FXCollections.observableArrayList();
		
		layoutHandlers = FXCollections.observableArrayList();
		
		selectedLayoutHandler = null;
		listeners = new ArrayList<ITabHandler>();
	}
	
	public static TabHandler getInstance() {
		if (instance == null) {
			instance = new TabHandler();
		}
		return instance;
	}
	
	public Boolean isEmpty() {
		return layoutHandlers.isEmpty();
	}
	
	public void selectLayoutHandler(int index) {
		if(index >= 0 && index < layoutHandlers.size()) {
			selectedLayoutHandler = layoutHandlers.get(index);
			handleTabHandlerEvent(ETabHandlerEvent.CHANGED_SELECTED_TAB);
		}
	}
	
	public void addLayoutHandler(LayoutHandler layoutHandler) {
		layoutHandlers.add(layoutHandler);
		handleTabHandlerEvent(ETabHandlerEvent.ADDED_NEW_TAB);
		selectedLayoutHandler = layoutHandler;
		handleTabHandlerEvent(ETabHandlerEvent.CHANGED_SELECTED_TAB);
	}
	
	public void removeLayoutHandler(LayoutHandler layoutHandler) {
		if(layoutHandlers.remove(layoutHandler)) {
			handleTabHandlerEvent(ETabHandlerEvent.REMOVED_TAB);
			if (layoutHandler == selectedLayoutHandler) {
				selectedLayoutHandler = null;
				handleTabHandlerEvent(ETabHandlerEvent.CHANGED_SELECTED_TAB);
			}
		}
	}
	
	public void fullReset() {
		zones.clear();
		extinguishers.clear();
		comments.clear();
				
		layoutHandlers.clear();
		LayoutHandler.resetNbrLayouts();
		
		UIZoneHandler.getInstance().reset();
		UIExtinguisherHandler.getInstance().reset();
		UITextHandler.getInstance().reset();
		
		UIElements.colorIndex = 1;
	}
	
	public ObservableList<Zone> getAllZones() {
		return zones;
	}
	
	public ObservableList<Extinguisher> getAllExtinguishers() {
		return extinguishers;
	}
	
	public LayoutHandler getSelectedLayoutHandler() {
		return selectedLayoutHandler;
	}

	public ObservableList<LayoutHandler> getLayoutHandlers() {
		return layoutHandlers;
	}
	
	@Override
	public void handleTabHandlerEvent(ETabHandlerEvent event) {
		for (ITabHandler listener : listeners) {
			listener.handleTabHandlerEvent(event);
		}
	}
	
	public void addListener(ITabHandler listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ITabHandler listener) {
		listeners.remove(listener);
	}	
	
	public int getHighestExtinguisherNumber() {
		if(layoutHandlers.size() == 0) {
			return 1;
		}
		
		int highestNumber = Integer.MIN_VALUE;
		for (LayoutHandler layoutHandler : layoutHandlers) {
			int highestLayoutNumber = layoutHandler.getHighestExtinguisherNumber();
			if (highestLayoutNumber > highestNumber) {
				highestNumber = highestLayoutNumber;
			}
		}
		return highestNumber + 1;
	}
	
	public int getHighestZoneNumber() {
		if(layoutHandlers.size() == 0) {
			return 1;
		}
		
		int highestNumber = Integer.MIN_VALUE;
		for (LayoutHandler layoutHandler : layoutHandlers) {
			int highestLayoutNumber = layoutHandler.getHighestZoneNumber();
			if (highestLayoutNumber > highestNumber) {
				highestNumber = highestLayoutNumber;
			}
		}
		return highestNumber + 1;
	}
	
}
