package com.projetpaparobin.frontend.agents.recapagent.converters;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.zones.Zone;

import javafx.util.StringConverter;

public class ZoneConverter extends StringConverter<Zone>{

	private static LayoutHandler layoutHandler = LayoutHandler.getInstance();
	
	@Override
	public String toString(Zone zone) {
		return zone.getId().getDefaultAreaName();
	}

	@Override
	public Zone fromString(String defaultZoneName) {
		return layoutHandler.getZoneFromDefaultZoneName(defaultZoneName);
	}

}
