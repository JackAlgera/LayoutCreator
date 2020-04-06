package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIExtinguisherText;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;

public class UITextHandler {

	private static UITextHandler instance;
	private ArrayList<UIZoneText> zoneTexts;
	private ArrayList<UIExtinguisherText> extinguisherTexts;
		
	private UITextHandler() {	
		zoneTexts = new ArrayList<UIZoneText>();
		extinguisherTexts = new ArrayList<UIExtinguisherText>();
	}
	
	public static UITextHandler getInstance() {
		if(instance == null) {
			instance = new UITextHandler();
		}
		
		return instance;
	}
	
	public boolean extinguisherHasText(Extinguisher ex) {
		for (UIExtinguisherText text : extinguisherTexts) {
			if(text.getExtinguisher().equals(ex)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean zoneHasText(Zone zone) {
		for (UIZoneText text : zoneTexts) {
			if(text.getZone().equals(zone)) {
				return true;
			}
		}
		
		return false;
	}
	
	public UIExtinguisherText getExtinguisherText(double posX, double posY) {
		for (UIExtinguisherText text : extinguisherTexts) {
			if(text.containsPoint(posX, posY)) {
				return text;
			}
		}
		return null;
	}
	
	public UIZoneText getZoneText(double posX, double posY) {
		for (UIZoneText text : zoneTexts) {
			if(text.containsPoint(posX, posY)) {
				return text;
			}
		}
		return null;
	}
	
	public ArrayList<UIExtinguisherText> getExtinguisherTexts() {
		return extinguisherTexts;
	}
	
	public ArrayList<UIZoneText> getZoneTexts() {
		return zoneTexts;
	}
	
	public void addExtinguisherText(UIExtinguisherText text) {
		extinguisherTexts.add(text);
	}
	
	public void addZoneText(UIZoneText text) {
		zoneTexts.add(text);
	}
	
}
