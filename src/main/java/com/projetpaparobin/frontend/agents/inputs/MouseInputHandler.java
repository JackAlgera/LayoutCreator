package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.elements.UIExtinguisher;
import com.projetpaparobin.frontend.elements.UIExtinguisherText;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInputHandler implements IZoneCreatorListener, IExtinguisherCreatorListener, EventHandler<MouseEvent> {

	private static MouseInputHandler instance;
	
	private ETypeAction state;
	private PresentationLayoutAgent presLayout;
	
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static UIExtinguisherHandler extinguisherHandler = UIExtinguisherHandler.getInstance();
	
	private Zone selectedZone = null;
	private UIZoneText selectedZoneText = null;
	private UIExtinguisher selectedExtinguisher = null;
	private UIExtinguisherText selectedExtinguisherText = null;
	private double dX = 0;
	private double dY = 0;
	
	private MouseInputHandler() {	
		state = ETypeAction.IDLE;
		ZoneCreator.getInstance().addListener(this);
		ExtinguisherCreator.getInstance().addListener(this);
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
				selectedExtinguisher = extinguisherHandler.getExtinguisher(event.getX(), event.getY());
				
				if(selectedExtinguisher != null) {
					dX = event.getX() - selectedExtinguisher.getPosX();
					dY = event.getY() - selectedExtinguisher.getPosY();
					state = ETypeAction.SELECTED_EXTINGUISHER;
					break;
				}
				selectedExtinguisherText = textHandler.getExtinguisherText(event.getX(), event.getY());
				if(selectedExtinguisherText != null) {
					dX = event.getX() - selectedExtinguisherText.getPosX();
					dY = event.getY() - selectedExtinguisherText.getPosY();
					state = ETypeAction.SELECTED_EXTINGUISHER_TEXT;
					break;
				}
				selectedZoneText = textHandler.getZoneText(event.getX(), event.getY());
				if(selectedZoneText != null) {
					dX = event.getX() - selectedZoneText.getPosX();
					dY = event.getY() - selectedZoneText.getPosY();
					state = ETypeAction.SELECTED_ZONE_TEXT;
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
				selectedZone = layoutHandler.getZone(event.getX(), event.getY());
				if(selectedZone != null) {
					extinguisherCreator.setPosition(new Point(event.getX(), event.getY()));
					extinguisherCreator.setZone(selectedZone);
				}
				break;
			}
			break;
			
		case "MOUSE_DRAGGED":
			double newPosX = event.getX() - dX;
			double newPosY = event.getY() - dY;
			
			switch (state) {
			case SELECTED_EXTINGUISHER:
				selectedExtinguisher.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_EXTINGUISHER_TEXT:
				selectedExtinguisherText.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_CORNER:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_ZONE_TEXT:
				selectedZoneText.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;			
			}
			break;			

		case "MOUSE_RELEASED":
			switch (state) {
			case SELECTED_EXTINGUISHER:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_CORNER:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_EXTINGUISHER_TEXT:
				state = ETypeAction.IDLE;
				break;	
			case SELECTED_ZONE_TEXT:
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

	@Override
	public void handleExtinguisherCreatorEvent(EExtinguisherEvents event) {
		switch (event) {
		case CREATING_NEW_EXTINGUISHER:
			state = ETypeAction.CREATING_EXTINGUISHER;
			break;
		case SETTING_NAME:
			break;
		case FINISHED_CREATING_EXTINGUISHER:
			state = ETypeAction.IDLE;
			break;
		case CANCELED:
			state = ETypeAction.IDLE;
			break;
		}
	}
	
}
