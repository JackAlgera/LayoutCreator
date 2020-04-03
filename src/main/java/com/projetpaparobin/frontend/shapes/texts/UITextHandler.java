package com.projetpaparobin.frontend.shapes.texts;

import java.util.ArrayList;

import com.projetpaparobin.zones.Zone;

public class UITextHandler {

	private static UITextHandler instance;
	private ArrayList<ZoneText> shapeTexts;
		
	private UITextHandler() {	
		shapeTexts = new ArrayList<ZoneText>();
	}
	
	public static UITextHandler getInstance() {
		if(instance == null) {
			instance = new UITextHandler();
		}
		
		return instance;
	}
	
	public boolean zoneHasText(Zone zone) {
		for (ZoneText text : shapeTexts) {
			if(text.getZone().equals(zone)) {
				return true;
			}
		}
		
		return false;
	}
	
	public ZoneText getText(double posX, double posY) {
		for (ZoneText text : shapeTexts) {
			if(text.containsPoint(posX, posY)) {
				return text;
			}
		}
		return null;
	}
	
	public ArrayList<ZoneText> getTexts() {
		return shapeTexts;
	}
	
	public void addText(ZoneText text) {
		shapeTexts.add(text);
	}
	
}
