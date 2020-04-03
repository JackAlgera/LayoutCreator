package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.zones.Point;
import com.projetpaparobin.zones.creators.EZoneEvents;
import com.projetpaparobin.zones.creators.IZoneCreatorListener;
import com.projetpaparobin.zones.creators.ZoneCreator;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInputHandler implements IZoneCreatorListener, EventHandler<MouseEvent> {

	private static MouseInputHandler instance;
	
	private ETypeAction state;
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	
	private MouseInputHandler() {	
		state = ETypeAction.IDLE;
		ZoneCreator.getInstance().addListener(this);
	}
	
	public static MouseInputHandler getInstance() {
		if(instance == null) {
			instance = new MouseInputHandler();
		}
		
		return instance;
	}
	
	public void setState(ETypeAction newState) {
		this.state = newState;
	}
	
	@Override
	public void handle(MouseEvent event) {
		System.out.print("Button:" + event.getButton() + " x=" + event.getX() + " y=" + event.getY() + " - ");
		System.out.println(LayoutHandler.getInstance().getZone(event.getX(), event.getY()));
				
		switch (state) {
		case IDLE:
			break;
		case CREATING_ZONE:
			zoneCreator.addPoint(new Point(event.getX(), event.getY()));	
			break;
		case CREATING_EXTINGUISHER:
			break;
		}
	}

	@Override
	public void handleZoneCreatorEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			state = ETypeAction.CREATING_ZONE;
			break;
		case ADDED_POINT:
			break;
		case SETTING_NAME:
			break;
		case FINISHED_CREATING_ZONE:
			state = ETypeAction.IDLE;
			break;
		case CANCELED:
			state = ETypeAction.IDLE;
			break;
		}
	}
	
}
