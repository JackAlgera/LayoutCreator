package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UICorner;
import com.projetpaparobin.frontend.elements.UIExtinguisher;
import com.projetpaparobin.frontend.elements.UIZone;
import com.projetpaparobin.frontend.elements.UIZoneText;

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
	
	public UICorner getExtinguisherResizeCorner(double posX, double posY) {
		for (UIExtinguisher ex : extinguishers) {
			if(!ex.isSelected()) {
				continue;
			}
			if(ex.getResizeCorner().containsPoint(posX, posY)) {
				return ex.getResizeCorner();
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
	
	public void reset() {
		extinguishers.clear();
	}
	
}
