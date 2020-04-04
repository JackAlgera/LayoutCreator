package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIElement;

public class UIZoneHandler {
	
	private static UIZoneHandler instance;
	private ArrayList<UIElement> shapes;
		
	private UIZoneHandler() {	
		shapes = new ArrayList<UIElement>();
	}
	
	public static UIZoneHandler getInstance() {
		if(instance == null) {
			instance = new UIZoneHandler();
		}
		
		return instance;
	}
	
	public ArrayList<UIElement> getZones() {
		return shapes;
	}
	
	public void add(UIElement zone) {
		shapes.add(zone);
	}
}
