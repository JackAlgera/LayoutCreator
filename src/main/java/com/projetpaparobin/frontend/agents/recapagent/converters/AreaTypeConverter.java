package com.projetpaparobin.frontend.agents.recapagent.converters;

import com.projetpaparobin.objects.zones.EAreaType;

import javafx.util.StringConverter;

public class AreaTypeConverter extends StringConverter<EAreaType> {

	@Override
	public String toString(EAreaType type) {
		return type.toString();
	}

	@Override
	public EAreaType fromString(String type) {
		return EAreaType.getEnum(type);
	}

}
