package com.projetpaparobin.frontend.agents.recapagent.converters;

import com.projetpaparobin.objects.extinguishers.EProtectionType;

import javafx.util.StringConverter;

public class ProtectionTypeConverter extends StringConverter<EProtectionType> {
	
	@Override
	public String toString(EProtectionType type) {
		return type.toString();
	}

	@Override
	public EProtectionType fromString(String type) {
		return EProtectionType.getEnum(type);
	}
	
}
