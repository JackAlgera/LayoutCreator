package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIConnection;
import com.projetpaparobin.frontend.elements.UIExtinguisherText;
import com.projetpaparobin.frontend.elements.UIZoneText;

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
	
	public UIConnection getConnection(double posX, double posY) {
		for (UIZoneText text : zoneTexts) {
			if(text.getConnection().containsPoint(posX, posY)) {
				return text.getConnection();
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
	
	public void reset() {
		extinguisherTexts.clear();
		zoneTexts.clear();
	}
	
}
