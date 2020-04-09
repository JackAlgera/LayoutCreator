package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.elements.UICorner;
import com.projetpaparobin.frontend.elements.UIExtinguisher;
import com.projetpaparobin.frontend.elements.UIExtinguisherText;
import com.projetpaparobin.frontend.elements.UIZone;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.Point;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInputHandler implements IZoneCreatorListener, IExtinguisherCreatorListener, EventHandler<MouseEvent> {

	private static MouseInputHandler instance;
	
	private ETypeAction state;
	private PresentationLayoutAgent presLayout;
	
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private static UIExtinguisherHandler extinguisherHandler = UIExtinguisherHandler.getInstance();
	
	private UIZone selectedZone = null;
	private UIZoneText selectedZoneText = null;
	private UIExtinguisher selectedExtinguisher = null;
	private UIExtinguisherText selectedExtinguisherText = null;
	private UICorner selectedCorner = null;
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
				selectedCorner = zoneHandler.getCorner(event.getX(), event.getY());
				if(selectedCorner != null) {
					dX = event.getX() - selectedCorner.getPosX();
					dY = event.getY() - selectedCorner.getPosY();
					state = ETypeAction.SELECTED_CORNER;
					break;
				}
				selectedZone = zoneHandler.getZone(event.getX(), event.getY());
				if(selectedZone != null) {
					dX = event.getX() - selectedZone.getPosX();
					dY = event.getY() - selectedZone.getPosY();
					state = ETypeAction.SELECTED_ZONE;
					presLayout.updateCanvas();
					break;
				}
				
				zoneHandler.removeSelectedZone();
				presLayout.updateCanvas();
				break;
			case CREATING_ZONE:
				zoneHandler.removeSelectedZone();
				zoneCreator.addPoint(new Point(event.getX(), event.getY()));	
				break;
			case CREATING_EXTINGUISHER:
				selectedZone = zoneHandler.getZone(event.getX(), event.getY());
				if(selectedZone != null) {
					extinguisherCreator.setPosition(new Point(event.getX(), event.getY()));
					extinguisherCreator.setZone(selectedZone.getZone());
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
				selectedCorner.translateShape(newPosX, newPosY);
				selectedCorner.getZone().getShape().updateArea();
				presLayout.updateCanvas();
				break;
			case SELECTED_ZONE:
				selectedZone.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
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
			case SELECTED_EXTINGUISHER_TEXT:
				state = ETypeAction.IDLE;
				break;	
			case SELECTED_CORNER:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_ZONE:
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
