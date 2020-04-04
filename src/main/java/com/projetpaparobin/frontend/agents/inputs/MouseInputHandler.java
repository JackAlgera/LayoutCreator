package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInputHandler implements IZoneCreatorListener, EventHandler<MouseEvent> {

	private static MouseInputHandler instance;
	
	private ETypeAction state;
	private PresentationLayoutAgent presLayout;
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	private Zone selectedZone = null;
	private UIZoneText selectedText = null;
	private double dX = 0;
	private double dY = 0;
	
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
	
	public void setPresLayoutAgent(PresentationLayoutAgent presLayout) {
		this.presLayout = presLayout;
	}
	
	public void setState(ETypeAction newState) {
		this.state = newState;
	}
	
	@Override
	public void handle(MouseEvent event) {
//		System.out.println("Button:" + event.getButton() + " x=" + event.getX() + " y=" + event.getY() + " - ");

		switch (event.getEventType().getName()) {
		case "MOUSE_PRESSED":
			switch (state) {
			case IDLE:
				selectedText = textHandler.getText(event.getX(), event.getY());
				if(selectedText != null) {
					dX = event.getX() - selectedText.getPosX();
					dY = event.getY() - selectedText.getPosY();
					state = ETypeAction.SELECTED_TEXT;
					break;
				}
				selectedZone = layoutHandler.getZone(event.getX(), event.getY());
				if(selectedZone != null) {
					state = ETypeAction.SELECTED_CORNER;
					break;
				}
				
				break;
			case CREATING_ZONE:
				zoneCreator.addPoint(new Point(event.getX(), event.getY()));	
				break;
			case CREATING_EXTINGUISHER:
				break;
			}
			break;
			
		case "MOUSE_DRAGGED":
			switch (state) {
			case SELECTED_CORNER:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_TEXT:
				selectedText.translateShape(event.getX() - dX, event.getY() - dY);
				presLayout.updateCanvas();
				break;			
			}
			break;			

		case "MOUSE_RELEASED":
			switch (state) {
			case SELECTED_CORNER:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_TEXT:
				System.out.println(event.getEventType().getName());
				state = ETypeAction.IDLE;
				break;			
			}
			break;
		default:
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
