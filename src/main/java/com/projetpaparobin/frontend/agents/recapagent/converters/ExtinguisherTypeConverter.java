package com.projetpaparobin.frontend.agents.recapagent.converters;

import com.projetpaparobin.objects.extinguishers.EExtinguisherType;

import javafx.util.StringConverter;

public class ExtinguisherTypeConverter extends StringConverter<EExtinguisherType> {

	@Override
	public String toString(EExtinguisherType type) {
		return type.toString();
	}

	@Override
	public EExtinguisherType fromString(String type) {
		return EExtinguisherType.getEnum(type);
	}

}