package com.projetpaparobin.frontend.agents.layout;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.elements.UIComment;
import com.projetpaparobin.frontend.elements.UIElement;
import com.projetpaparobin.frontend.elements.UIExtinguisher;
import com.projetpaparobin.frontend.elements.UIExtinguisherText;
import com.projetpaparobin.frontend.elements.UIZone;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.frontend.handlers.UICommentHandler;
import com.projetpaparobin.frontend.handlers.UIExtinguisherHandler;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.Comment;
import com.projetpaparobin.objects.creators.comments.ECommentEvent;
import com.projetpaparobin.objects.creators.comments.ICommentCreatorListener;
import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class PresentationLayoutAgent implements ITabHandler, IZoneCreatorListener, IExtinguisherCreatorListener, ICommentCreatorListener {

	private static LayoutHandler layoutHandler;	
	
	private static TabHandler tabHandler = TabHandler.getInstance();
	
	private static UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static UIExtinguisherHandler extinguisherHandler = UIExtinguisherHandler.getInstance();
	private static UICommentHandler commentHandler = UICommentHandler.getInstance();
	
	private ViewLayoutAgent view;
	
	private boolean shouldDrawNewEx, shouldDrawOldEx, shouldDrawZones, shouldDrawComments;
	
	public PresentationLayoutAgent() {		
		shouldDrawNewEx = true;
		shouldDrawOldEx = true;
		shouldDrawZones = true;
		shouldDrawComments = true;
		
		MouseInputHandler.getInstance().setPresLayoutAgent(this);
		tabHandler.addListener(this);
	}
	
	public void updateCanvas() {
		view.cleanCanvas();
		
		for (UIElement zone : zoneHandler.getZones()) {
			zone.drawShape();
		}
		for (UIElement text : textHandler.getZoneTexts()) {
			text.drawShape();
		}
		for (UIElement extinguisher : extinguisherHandler.getExtinguishers()) {
			extinguisher.drawShape();
		}
		for (UIElement comment : commentHandler.getComments()) {
			comment.drawShape();
		}
	}
	
	public void updateShapes() {
		if (layoutHandler == null) {
			return;
		}
		
		zoneHandler.getZones().clear();
		extinguisherHandler.getExtinguishers().clear();
		textHandler.getExtinguisherTexts().clear();
		textHandler.getZoneTexts().clear();
		commentHandler.getComments().clear();
		
		for (Zone zone : layoutHandler.getZones()) {
			if(shouldDrawZones) {
				UIZone uiZone = new UIZone(layoutHandler, zone, view, false);
				uiZone.switchPointRadius();
				zoneHandler.add(uiZone);
				
				UIZoneText uiZoneText = new UIZoneText(layoutHandler, zone, uiZone, view);
				uiZone.setUiText(uiZoneText);
				textHandler.addZoneText(uiZoneText);	
			}					
			
			for (Extinguisher ex : zone.getExtinguishers()) {
				if((shouldDrawOldEx && !ex.getIsNew()) || (shouldDrawNewEx && ex.getIsNew())) {
					UIExtinguisher uiEx = new UIExtinguisher(layoutHandler, ex, view);
					UIExtinguisherText uiExText = new UIExtinguisherText(layoutHandler, ex, view);
					uiEx.setUiExText(uiExText);
					extinguisherHandler.addExtinguisher(uiEx);
					textHandler.addExtinguisherText(uiExText);
				}				
			}
		} 
		
		if(shouldDrawComments) {
			for (Comment comment : layoutHandler.getComments()) {
				commentHandler.addComment(new UIComment(layoutHandler, comment, view));
			}			
		}
		updateCanvas();
	}

	public void showLayoutWithoutZones() {
		this.shouldDrawNewEx = true;
		this.shouldDrawOldEx = true;
		this.shouldDrawZones = false;
		this.shouldDrawComments = true;
		updateShapes();
	}
	
	public void showLayoutWithoutZonesAndOldExtinguishers() {
		this.shouldDrawNewEx = true;
		this.shouldDrawOldEx = false;
		this.shouldDrawZones = false;
		this.shouldDrawComments = true;
		updateShapes();
	}
	
	public void setShouldDrawEverything(boolean shouldDraw) {
		this.shouldDrawNewEx = shouldDraw;
		this.shouldDrawOldEx = shouldDraw;
		this.shouldDrawZones = shouldDraw;
		this.shouldDrawComments = shouldDraw;
		updateShapes();
	}
	
	public void setShouldDrawNewEx(boolean shouldDraw) {
		this.shouldDrawNewEx = shouldDraw;
		updateShapes();
	}
	
	public void setShouldDrawOldEx(boolean shouldDraw) {
		this.shouldDrawOldEx = shouldDraw;
		updateShapes();
	}
	
	public void setShouldDrawZones(boolean shouldDraw) {
		this.shouldDrawZones = shouldDraw;
		updateShapes();
	}
	
	public void setShouldDrawComments(boolean shouldDraw) {
		this.shouldDrawComments = shouldDraw;
		updateShapes();
	}
	
	public WritableImage getSnapshot(SnapshotParameters params, WritableImage image) {
		return view.getSnapshot(params, image);
	}
	
	public void setView(ViewLayoutAgent view) {
		this.view = view;
	}	
	
	@Override
	public void handleZoneCreatorEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			break;
		case ADDED_POINT:
			updateCanvas();			
			UIZone currentZone = new UIZone(layoutHandler, layoutHandler.getZoneCreator().getCurrentZone(), view, true);
			currentZone.drawShape();
			break;
		case SETTING_NAME:
			break;
		case FINISHED_CREATING_ZONE:
			updateShapes();
			break;
		case CANCELED:
			updateShapes();
			break;
		}		
	}

	@Override
	public void handleExtinguisherCreatorEvent(EExtinguisherEvents event) {
		switch (event) {
		case CREATING_NEW_EXTINGUISHER:
			break;
		case SETTING_NAME:
			break;
		case FINISHED_CREATING_EXTINGUISHER:
			updateShapes();
			break;
		case CANCELED:
			updateCanvas();	
			break;
		}
	}

	@Override
	public void handleCommentCreatorEvent(ECommentEvent event) {
		switch (event) {
		case CREATING_NEW_COMMENT:
			break;
		case SETTING_TEXT:
			break;
		case FINISHED_CREATING_COMMENT:
			updateShapes();
			break;
		case CANCELED:
			updateShapes();
			break;
		}
	}

	@Override
	public void handleTabHAndlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
			break;
		case CHANGED_SELECTED_TAB:
			if(layoutHandler != null) {
				layoutHandler.getZoneCreator().removeListener(this);
				layoutHandler.getCommentCreator().removeListener(this);
				layoutHandler.getExtinguisherCreator().removeListener(this);
			}
			layoutHandler = tabHandler.getSelectedLayoutHandler();
			if(layoutHandler != null) {
				layoutHandler.getZoneCreator().addListener(this);
				layoutHandler.getCommentCreator().addListener(this);
				layoutHandler.getExtinguisherCreator().addListener(this);
			}
			break;
		case REMOVED_TAB:
			break;
		case FULL_RESET:
			break;
		}
	}
	
}
