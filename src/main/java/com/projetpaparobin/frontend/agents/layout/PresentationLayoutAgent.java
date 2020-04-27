package com.projetpaparobin.frontend.agents.layout;

import com.projetpaparobin.documents.LayoutHandler;
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
import com.projetpaparobin.objects.creators.comments.CommentCreator;
import com.projetpaparobin.objects.creators.comments.ECommentEvent;
import com.projetpaparobin.objects.creators.comments.ICommentCreatorListener;
import com.projetpaparobin.objects.creators.extinguishers.EExtinguisherEvents;
import com.projetpaparobin.objects.creators.extinguishers.ExtinguisherCreator;
import com.projetpaparobin.objects.creators.extinguishers.IExtinguisherCreatorListener;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class PresentationLayoutAgent implements IZoneCreatorListener, IExtinguisherCreatorListener, ICommentCreatorListener {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	private static CommentCreator commentCreator = CommentCreator.getInstance();
	
	private static UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static UIExtinguisherHandler extinguisherHandler = UIExtinguisherHandler.getInstance();
	private static UICommentHandler commentHandler = UICommentHandler.getInstance();
	
	private ViewLayoutAgent view;
	
	public PresentationLayoutAgent() {
		zoneCreator.addListener(this);
		extinguisherCreator.addListener(this);
		commentCreator.addListener(this);
		
		MouseInputHandler.getInstance().setPresLayoutAgent(this);
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
		for (UIElement text : textHandler.getExtinguisherTexts()) {
			text.drawShape();
		}
		for (UIElement comment : commentHandler.getComments()) {
			comment.drawShape();
		}
	}
	
	public void updateShapes() {
		zoneHandler.getZones().clear();
		extinguisherHandler.getExtinguishers().clear();
		textHandler.getExtinguisherTexts().clear();
		textHandler.getZoneTexts().clear();
		commentHandler.getComments().clear();
		
		for (Zone zone : layoutHandler.getZones()) {
			UIZone uiZone = new UIZone(zone, view, false);
			uiZone.switchPointRadius();
			zoneHandler.add(uiZone);
			
			UIZoneText uiZoneText = new UIZoneText(zone, uiZone, view);
			uiZone.setUiText(uiZoneText);
			textHandler.addZoneText(uiZoneText);			
			
			for (Extinguisher ex : zone.getExtinguishers()) {
				UIExtinguisher uiEx = new UIExtinguisher(ex, view);
				UIExtinguisherText uiExText = new UIExtinguisherText(ex, view);
				uiEx.setUiExText(uiExText);
				extinguisherHandler.addExtinguisher(uiEx);
				textHandler.addExtinguisherText(uiExText);
			}
		} 

		for (Comment comment : layoutHandler.getComments()) {
			commentHandler.addComment(new UIComment(comment, view));
		}
		updateCanvas();
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
			UIZone currentZone = new UIZone(zoneCreator.getCurrentZone(), view, true);
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
	
}
