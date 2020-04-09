package com.projetpaparobin.frontend.agents.layout;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.elements.UIElement;
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
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;

public class PresentationLayoutAgent implements IZoneCreatorListener, IExtinguisherCreatorListener {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private static ExtinguisherCreator extinguisherCreator = ExtinguisherCreator.getInstance();
	
	private static UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static UIExtinguisherHandler extinguisherHandler = UIExtinguisherHandler.getInstance();
	
	private IViewLayoutAgent view;
	
	public PresentationLayoutAgent() {
		zoneCreator.addListener(this);
		extinguisherCreator.addListener(this);
		
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
	}
	
	public void updateShapes() {
		zoneHandler.getZones().clear();
		extinguisherHandler.getExtinguishers().clear();
		
		for (Zone zone : layoutHandler.getZones()) {
			UIZone uiZone = new UIZone(zone, view.getCanvas(), false);
			uiZone.switchPointRadius();
			zoneHandler.add(uiZone);
			
			if(!textHandler.zoneHasText(zone)) {
				textHandler.addZoneText(new UIZoneText(zone, view.getCanvas()));
			}		

			for (Extinguisher ex : zone.getExtinguishers()) {
				extinguisherHandler.addExtinguisher(new UIExtinguisher(ex, view.getCanvas()));
				
				if(!textHandler.extinguisherHasText(ex)) {
					textHandler.addExtinguisherText(new UIExtinguisherText(ex, view.getCanvas()));
				}	
			}
		} 
	}
			
	public void setView(IViewLayoutAgent view) {
		this.view = view;
	}	
	
	@Override
	public void handleZoneCreatorEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			break;
		case ADDED_POINT:
			updateCanvas();			
			UIZone currentZone = new UIZone(zoneCreator.getCurrentZone(), view.getCanvas(), true);
			currentZone.drawShape();
			break;
		case SETTING_NAME:
			break;
		case FINISHED_CREATING_ZONE:
			updateShapes();
			updateCanvas();		
			break;
		case CANCELED:
			updateCanvas();		
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
			updateCanvas();	
			break;
		case CANCELED:
			updateCanvas();	
			break;
		}
	}
	
}
