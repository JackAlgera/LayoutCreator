package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.elements.UICorner;
import com.projetpaparobin.frontend.elements.UIElement;
import com.projetpaparobin.frontend.elements.UIZone;
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
	
	private UIElement selectedUIElement = null;
	
	private UIZone selectedZone = null;
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
			if(selectedUIElement != null) {
				selectedUIElement.setIsSelected(false);
			}
			
			switch (state) {
			case IDLE:
				selectedUIElement = extinguisherHandler.getExtinguisher(event.getX(), event.getY());				
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_EXTINGUISHER, event);
					break;
				}
				selectedUIElement = textHandler.getExtinguisherText(event.getX(), event.getY());
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_EXTINGUISHER_TEXT, event);
					break;
				}
				selectedUIElement = textHandler.getZoneText(event.getX(), event.getY());
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_ZONE_TEXT, event);
					break;
				}
				selectedUIElement = zoneHandler.getCorner(event.getX(), event.getY());
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_CORNER, event);
					break;
				}
				selectedUIElement = zoneHandler.getZone(event.getX(), event.getY());
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_ZONE, event);
					break;
				}
				
				presLayout.updateCanvas();
				break;
			case CREATING_ZONE:
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
				selectedUIElement.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_EXTINGUISHER_TEXT:
				selectedUIElement.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_CORNER:
				selectedUIElement.translateShape(newPosX, newPosY);
				((UICorner) selectedUIElement).getZone().getShape().updateArea();
				presLayout.updateCanvas();
				break;
			case SELECTED_ZONE:
				selectedUIElement.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_ZONE_TEXT:
				selectedUIElement.translateShape(newPosX, newPosY);
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

	private void selectUIElement(ETypeAction actionType, MouseEvent event) {
		if(selectedUIElement != null) {
			dX = event.getX() - selectedUIElement.getPosX();
			dY = event.getY() - selectedUIElement.getPosY();
			state = actionType;
			selectedUIElement.setIsSelected(true);
			presLayout.updateCanvas();
		}
	}
	
	public void cancelSelection() {
		if(selectedUIElement != null) {
			selectedUIElement.setIsSelected(false);
		}
	}
	
	public void deleteSelectedElement() {
		if(selectedUIElement != null) {
			selectedUIElement.removeSelf();
			selectedUIElement = null;
			presLayout.updateShapes();
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
