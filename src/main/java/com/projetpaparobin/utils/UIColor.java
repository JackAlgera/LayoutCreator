package com.projetpaparobin.utils;

import javafx.scene.paint.Color;

public enum UIColor {

	RED("Red", Color.RED), BLUE("Blue", Color.BLUE), YELLOW("Yellow", Color.YELLOW), GREEN("Green", Color.GREEN);
	
	private Color color;
	private String colorName;
	
	private UIColor(String colorName, Color color) {
		this.colorName = colorName;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return colorName;
	}
	
	public static UIColor getEnum(Color color) {
		if(color.equals(Color.RED)) {
			return RED;
		} else if(color.equals(Color.BLUE)) {
			return BLUE;
		} else if(color.equals(Color.YELLOW)) {
			return YELLOW;
		} else {
			return GREEN;
		}
	}
	
}
