package com.projetpaparobin.frontend.agents.layout;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.elements.UIElement;
import com.projetpaparobin.frontend.elements.UIZone;
import com.projetpaparobin.frontend.elements.UIZoneText;
import com.projetpaparobin.frontend.elements.shapes.UIPolygon;
import com.projetpaparobin.frontend.handlers.UITextHandler;
import com.projetpaparobin.frontend.handlers.UIZoneHandler;
import com.projetpaparobin.objects.creators.zones.EZoneEvents;
import com.projetpaparobin.objects.creators.zones.IZoneCreatorListener;
import com.projetpaparobin.objects.creators.zones.ZoneCreator;
import com.projetpaparobin.objects.zones.Zone;

public class PresentationLayoutAgent implements IZoneCreatorListener {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	
	private static UIZoneHandler zoneHandler = UIZoneHandler.getInstance();
	private static UITextHandler textHandler = UITextHandler.getInstance();
	
	private IViewLayoutAgent view;
	
	public PresentationLayoutAgent() {
		zoneCreator.addListener(this);
		MouseInputHandler.getInstance().setPresLayoutAgent(this);
	}
	
	public void updateCanvas() {
		view.cleanCanvas();
		
		for (UIElement zone : zoneHandler.getZones()) {
			zone.drawShape();
		}
		for (UIElement text : textHandler.getTexts()) {
			text.drawShape();
		}
	}
	
	public void updateShapes() {
		zoneHandler.getZones().clear();
		
		for (Zone zone : layoutHandler.getZones()) {
			zoneHandler.add(new UIZone(zone.getShape().getPoints(), zone.getRimColor(), zone.getFillColor(), view.getCanvas()));
			
			if(!textHandler.zoneHasText(zone)) {
				textHandler.addText(new UIZoneText(zone, view.getCanvas()));
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
			UIPolygon currentZone = new UIPolygon(zoneCreator.getCurrentZone().getShape().getPoints(),
					zoneCreator.getCurrentZone().getRimColor(), 
					zoneCreator.getCurrentZone().getFillColor(), 
					view.getCanvas());
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
	
}
