package com.projetpaparobin.frontend.agents.layout;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.inputs.MouseInputHandler;
import com.projetpaparobin.frontend.shapes.UIPolygon;
import com.projetpaparobin.frontend.shapes.UIShape;
import com.projetpaparobin.frontend.shapes.texts.ZoneText;
import com.projetpaparobin.frontend.shapes.texts.UITextHandler;
import com.projetpaparobin.zones.Zone;
import com.projetpaparobin.zones.creators.EZoneEvents;
import com.projetpaparobin.zones.creators.IZoneCreatorListener;
import com.projetpaparobin.zones.creators.ZoneCreator;

public class PresentationLayoutAgent implements IZoneCreatorListener {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static UITextHandler textHandler = UITextHandler.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private IViewLayoutAgent view;
	private ArrayList<UIShape> shapes;
	
	public PresentationLayoutAgent() {
		shapes = new ArrayList<UIShape>();
		zoneCreator.addListener(this);
		MouseInputHandler.getInstance().setPresLayoutAgent(this);
	}
	
	public void updateCanvas() {
		view.cleanCanvas();
		
		for (UIShape shape : shapes) {
			shape.drawShape();
		}
		for (UIShape text : textHandler.getTexts()) {
			text.drawShape();
		}
	}
	
	public void updateShapes() {
		shapes.clear();
		
		for (Zone zone : layoutHandler.getZones()) {
			shapes.add(new UIPolygon(zone.getShape().getPoints(), zone.getRimColor(), zone.getFillColor(), view.getCanvas()));
			
			if(!textHandler.zoneHasText(zone)) {
				textHandler.addText(new ZoneText(zone, view.getCanvas()));
			}			
		} 
	}
	
	public void addShape(UIShape shape) {
		shape.addCanvas(view.getCanvas());
		shapes.add(shape);
		updateCanvas();
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
