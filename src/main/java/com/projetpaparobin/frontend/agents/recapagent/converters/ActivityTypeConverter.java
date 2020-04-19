package com.projetpaparobin.frontend.agents.recapagent.converters;

import com.projetpaparobin.objects.zones.EActivityType;

import javafx.util.StringConverter;

public class ActivityTypeConverter extends StringConverter<EActivityType> {

	@Override
	public String toString(EActivityType type) {
		return type.toString();
	}

	@Override
	public EActivityType fromString(String type) {
		return EActivityType.getEnum(type);
	}
	
}
