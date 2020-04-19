package com.projetpaparobin.frontend.agents.recapagent.converters;

import com.projetpaparobin.utils.UIColor;

import javafx.util.StringConverter;

public class UIColorConverter extends StringConverter<UIColor> {
	
	@Override
	public String toString(UIColor type) {
		return type.toString();
	}

	@Override
	public UIColor fromString(String type) {
		return UIColor.getEnum(type);
	}
	
}
