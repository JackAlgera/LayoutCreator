package com.projetpaparobin.frontend.agents.layout;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.shapes.Polygon;
import com.projetpaparobin.frontend.shapes.UIShape;
import com.projetpaparobin.zones.Point;
import com.projetpaparobin.zones.Zone;
import com.projetpaparobin.zones.creators.EZoneEvents;
import com.projetpaparobin.zones.creators.IZoneCreatorListener;
import com.projetpaparobin.zones.creators.ZoneCreator;

public class PresentationLayoutAgent implements IZoneCreatorListener {

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	private static ZoneCreator zoneCreator = ZoneCreator.getInstance();
	private IViewLayoutAgent view;
	private ArrayList<UIShape> shapes;
	
	public PresentationLayoutAgent() {
		shapes = new ArrayList<UIShape>();
		zoneCreator.addListener(this);
	}
	
	public void updateCanvas() {
		view.cleanCanvas();
		
		for (UIShape shape : shapes) {
			shape.drawShape();
		}
	}
	
	public void updateShapes() {
		shapes.clear();
		
		for (Zone zone : layoutHandler.getZones()) {
			shapes.add(new Polygon(zone.getShape().getPoints(), zone.getRimColor(), zone.getFillColor(), view.getCanvas()));
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
	public void handleEvent(EZoneEvents event) {
		switch (event) {
		case CREATING_NEW_ZONE:
			break;
		case ADDED_POINT:
			updateCanvas();			
			Polygon currentZone = new Polygon(zoneCreator.getCurrentZone().getShape().getPoints(),
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
