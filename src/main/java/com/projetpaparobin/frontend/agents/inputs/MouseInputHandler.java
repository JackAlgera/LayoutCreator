package com.projetpaparobin.frontend.agents.inputs;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.frontend.elements.UIComment;
import com.projetpaparobin.frontend.elements.UICorner;
import com.projetpaparobin.frontend.elements.UIElement;
import com.projetpaparobin.frontend.elements.UIExtinguisher;
import com.projetpaparobin.frontend.elements.UIExtinguisherText;
import com.projetpaparobin.frontend.elements.UIZone;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.frontend.handlers.UICommentHandler;
import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.creators.comments.CommentCreator;
import com.projetpaparobin.objects.creators.comments.ECommentEvent;
import com.projetpaparobin.objects.creators.comments.ICommentCreatorListener;
import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.Point;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseInputHandler implements IZoneCreatorListener, IExtinguisherCreatorListener, ICommentCreatorListener, EventHandler<MouseEvent> {

	private static MouseInputHandler instance;
	
	private ETypeAction state;
	private PresentationLayoutAgent presLayout;
	
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static CommentCreator commentCreator = CommentCreator.getInstance();
	
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private static UIExtinguisherHandler extinguisherHandler = UIExtinguisherHandler.getInstance();
	private static UICommentHandler commentHandler = UICommentHandler.getInstance();
	
	private ViewLayoutAgent viewLayoutAgent;
	private UIElement selectedUIElement = null;
	private UIElement prevSelectedElement = null;
	
	private UIZone selectedZone = null;
	private double dX = 0;
	private double dY = 0;
	
	private MouseInputHandler() {	
		state = ETypeAction.IDLE;
		zoneCreator.addListener(this);
		extinguisherCreator.addListener(this);
		commentCreator.addListener(this);
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
	
	public void setViewLayoutAgent(ViewLayoutAgent viewLayoutAgent) {
		this.viewLayoutAgent = viewLayoutAgent;
	}
	
	@Override
	public void handle(MouseEvent event) {
//		System.out.println("Button:" + event.getButton() + " x=" + (event.getX() / viewLayoutAgent.getCanvasWidth()) + " y=" + (event.getY() / viewLayoutAgent.getCanvasHeight()));

		double mouseX = event.getX() / viewLayoutAgent.getCanvasWidth();
		double mouseY = event.getY() / viewLayoutAgent.getCanvasHeight();
		
		switch (event.getEventType().getName()) {
		case "MOUSE_PRESSED":
			prevSelectedElement = selectedUIElement;
			
			switch (state) {
			case IDLE:
				selectedUIElement = commentHandler.getCommentResizeCorner(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_COMMENT_TEXT_RESIZE_CORNER, mouseX, mouseY, prevSelectedElement, false);
					break;
				}
				selectedUIElement = commentHandler.getComment(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_COMMENT, mouseX, mouseY, prevSelectedElement, true);
					break;
				}
				selectedUIElement = textHandler.getExtinguisherTextResizeCorner(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_EXTINGUISHER_TEXT_RESIZE_CORNER, mouseX, mouseY, prevSelectedElement, false);
					break;
				}
				selectedUIElement = textHandler.getExtinguisherText(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_EXTINGUISHER_TEXT, mouseX, mouseY, prevSelectedElement, true);
					break;
				}
				selectedUIElement = extinguisherHandler.getExtinguisherResizeCorner(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_EXTINGUISHER_RESIZE_CORNER, mouseX, mouseY, prevSelectedElement, false);
					break;
				}
				selectedUIElement = extinguisherHandler.getExtinguisher(mouseX, mouseY);				
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_EXTINGUISHER, mouseX, mouseY, prevSelectedElement, true);
					break;
				}
				selectedUIElement = textHandler.getZoneTextResizeCorner(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_ZONE_TEXT_RESIZE_CORNER, mouseX, mouseY, prevSelectedElement, false);
					break;
				}
				selectedUIElement = textHandler.getZoneText(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_ZONE_TEXT, mouseX, mouseY, prevSelectedElement, true);
					break;
				}
				selectedUIElement = textHandler.getConnection(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_CONNECTION, mouseX, mouseY, prevSelectedElement, true);
					break;
				}
				selectedUIElement = zoneHandler.getCorner(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_CORNER, mouseX, mouseY, prevSelectedElement, false);
					break;
				}
				selectedUIElement = zoneHandler.getZone(mouseX, mouseY);
				if(selectedUIElement != null) {
					selectUIElement(ETypeAction.SELECTED_ZONE, mouseX, mouseY, prevSelectedElement, true);
					break;
				}
				if(prevSelectedElement != null) {
					prevSelectedElement.setIsSelected(false);
				}
				presLayout.updateCanvas();
				break;
			case CREATING_ZONE:
				zoneCreator.addPoint(new Point(mouseX, mouseY));	
				break;
			case CREATING_EXTINGUISHER:
				selectedZone = zoneHandler.getZone(mouseX, mouseY);
				if(selectedZone != null) {
					extinguisherCreator.setPosition(new Point(mouseX, mouseY));
					extinguisherCreator.setZone(selectedZone.getZone());
				}
			case CREATING_COMMENT:
				commentCreator.setPosition(new Point(mouseX, mouseY));
				break;
			}
			break;
			
		case "MOUSE_DRAGGED":
			double newPosX = mouseX - dX;
			double newPosY = mouseY - dY;
			
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
				((UIZone) ((UICorner) selectedUIElement).getUiElement()).updateZone();
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
			case SELECTED_COMMENT:
				selectedUIElement.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_CONNECTION:
				selectedUIElement.translateShape(newPosX, newPosY);
				presLayout.updateCanvas();
				break;		
			case SELECTED_ZONE_TEXT_RESIZE_CORNER:
				selectedUIElement.resize(newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_EXTINGUISHER_TEXT_RESIZE_CORNER:
				selectedUIElement.resize(newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_EXTINGUISHER_RESIZE_CORNER:
				selectedUIElement.resize(newPosY);
				presLayout.updateCanvas();
				break;
			case SELECTED_COMMENT_TEXT_RESIZE_CORNER:
				selectedUIElement.resize(newPosY);
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
				if(prevSelectedElement != null) {
					selectedUIElement = prevSelectedElement;
				}
				break;
			case SELECTED_ZONE:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_ZONE_TEXT:
				state = ETypeAction.IDLE;
				break;		
			case SELECTED_COMMENT:
				state = ETypeAction.IDLE;
				break;	
			case SELECTED_CONNECTION:
				state = ETypeAction.IDLE;
				break;
			case SELECTED_ZONE_TEXT_RESIZE_CORNER:
				state = ETypeAction.IDLE;
				if(prevSelectedElement != null) {
					selectedUIElement = prevSelectedElement;
				}
				break;
			case SELECTED_EXTINGUISHER_TEXT_RESIZE_CORNER:
				state = ETypeAction.IDLE;
				if(prevSelectedElement != null) {
					selectedUIElement = prevSelectedElement;
				}
				break;
			case SELECTED_EXTINGUISHER_RESIZE_CORNER:
				state = ETypeAction.IDLE;
				if(prevSelectedElement != null) {
					selectedUIElement = prevSelectedElement;
				}
				break;
			case SELECTED_COMMENT_TEXT_RESIZE_CORNER:
				state = ETypeAction.IDLE;
				if(prevSelectedElement != null) {
					selectedUIElement = prevSelectedElement;
				}
				break;
			}
			break;
		default:
			break;
		}
	}

	private void selectUIElement(ETypeAction actionType, double mouseX, double mouseY, UIElement prevSelectedElement, boolean shouldDeselect) {
		if(selectedUIElement != null) {
			if(prevSelectedElement != null && shouldDeselect) {
				prevSelectedElement.setIsSelected(false);
			}
			dX = mouseX - selectedUIElement.getPosX();
			dY = mouseY - selectedUIElement.getPosY();
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

	@Override
	public void handleCommentCreatorEvent(ECommentEvent event) {
		switch (event) {
		case CREATING_NEW_COMMENT:
			state = ETypeAction.CREATING_COMMENT;
			break;
		case SETTING_TEXT:
			break;
		case FINISHED_CREATING_COMMENT:
			state = ETypeAction.IDLE;
			break;
		case CANCELED:
			state = ETypeAction.IDLE;
			break;
		}
	}
	
}
