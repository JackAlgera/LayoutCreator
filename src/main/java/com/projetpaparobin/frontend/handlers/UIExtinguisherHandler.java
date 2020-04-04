package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIExtinguisher;

public class UIExtinguisherHandler {

	private static UIExtinguisherHandler instance;
	private ArrayList<UIExtinguisher> extinguishers;
		
	private UIExtinguisherHandler() {	
		extinguishers = new ArrayList<UIExtinguisher>();
	}
	
	public static UIExtinguisherHandler getInstance() {
		if(instance == null) {
			instance = new UIExtinguisherHandler();
		}
		
		return instance;
	}
	
	public UIExtinguisher getExtinguisher(double posX, double posY) {
		for (UIExtinguisher e : extinguishers) {
			if(e.containsPoint(posX, posY)) {
				return e;
			}
		}
		return null;
	}
	
	public ArrayList<UIExtinguisher> getExtinguishers() {
		return extinguishers;
	}
	
	public void addExtinguisher(UIExtinguisher e) {
		extinguishers.add(e);
	}
	
}
