package com.projetpaparobin.documents;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.Comment;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class LayoutHandler implements ILayoutHandlerListener {

	private static LayoutHandler instance;

	private ObservableList<Zone> zones;
	private ObservableList<Extinguisher> extinguishers;
	private ObservableList<Comment> comments;

	private BufferedImage bufImage;
	
	private ArrayList<ILayoutHandlerListener> listeners;
	
	private LayoutHandler() {		
		zones = FXCollections.observableArrayList();
		extinguishers = FXCollections.observableArrayList();		
		comments = FXCollections.observableArrayList();
		bufImage = null;
		listeners = new ArrayList<ILayoutHandlerListener>();
	}
	
	public static LayoutHandler getInstance() {
		if(instance == null) {
			instance = new LayoutHandler();
		}
		
		return instance;
	}
	
	public void addZonesListListener(ListChangeListener<Zone> listener) {
		zones.addListener(listener);
	}
	
	public void setBufImage(BufferedImage bufImage) {
		this.bufImage = bufImage;
		layoutImageUpdated();
	}
	
	public BufferedImage getBufImage() {
		return bufImage;
	}
	
	public Zone getZoneFromDefaultZoneName(String name) {
		for (Zone zone : zones) {
			if(zone.getId().getDefaultAreaName().strip().toLowerCase().equals(name.strip().toLowerCase())) {
				return zone;
			}
		}
		return (zones.size() > 0 ? zones.get(0) : null);
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
	
	public void removeExtinguisher(Extinguisher ex) {
		extinguishers.remove(ex);
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
		
	public void addComment(Comment comment) {
		comments.add(comment);
	}	
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
	}
	
	public void setComments(ArrayList<Comment> comments) {
		this.comments.clear();
		for (Comment comment : comments) {
			addComment(comment);
		}
	}
	
	public ObservableList<Comment> getComments() {
		return comments;
	}
	
	public void fullReset() {
		zones.clear();
		extinguishers.clear();
		comments.clear();
		ZoneCreator.getInstance().reset();
		ExtinguisherCreator.getInstance().reset();
		
		UIZoneHandler.getInstance().reset();
		UIExtinguisherHandler.getInstance().reset();
		UITextHandler.getInstance().reset();
		
		UIElements.colorIndex = 1;
	}

	public void addListener(ILayoutHandlerListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ILayoutHandlerListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void layoutImageUpdated() {
		for (ILayoutHandlerListener listener : listeners) {
			listener.layoutImageUpdated();
		}
	}
	
	public int getHighestZoneNumber() {
		if(zones.size() == 0) {
			return 1;
		}
		
		int highestNumber = Integer.MIN_VALUE;
		for (Zone zone : zones) {
			if(zone.getId().getAreaNumber() > highestNumber) {
				highestNumber = zone.getId().getAreaNumber();
			}
		}
		return highestNumber + 1;
	}
	
}
