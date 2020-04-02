package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.zones.Point;
import com.projetpaparobin.zones.creators.ZoneCreator;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInputHandler implements EventHandler<MouseEvent>{

	private static MouseInputHandler instance;
	
	private ETypeAction state;
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	
	private MouseInputHandler() {		
	}
	
	public static MouseInputHandler getInstance() {
		if(instance == null) {
			instance = new MouseInputHandler();
		}
		
		return instance;
	}
	
	@Override
	public void handle(MouseEvent event) {
		System.out.println("Button:" + event.getButton() + " x=" + event.getX() + " y=" + event.getY());
		zoneCreator.addPoint(new Point(event.getX(), event.getY()));		
	}
	
}
