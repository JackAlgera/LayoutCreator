package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.objects.zones.Zone;

public class UITextHandler {

	private static UITextHandler instance;
	private ArrayList<UIZoneText> shapeTexts;
		
	private UITextHandler() {	
		shapeTexts = new ArrayList<UIZoneText>();
	}
	
	public static UITextHandler getInstance() {
		if(instance == null) {
			instance = new UITextHandler();
		}
		
		return instance;
	}
	
	public boolean zoneHasText(Zone zone) {
		for (UIZoneText text : shapeTexts) {
			if(text.getZone().equals(zone)) {
				return true;
			}
		}
		
		return false;
	}
	
	public UIZoneText getText(double posX, double posY) {
		for (UIZoneText text : shapeTexts) {
			if(text.containsPoint(posX, posY)) {
				return text;
			}
		}
		return null;
	}
	
	public ArrayList<UIZoneText> getTexts() {
		return shapeTexts;
	}
	
	public void addText(UIZoneText text) {
		shapeTexts.add(text);
	}
	
}
