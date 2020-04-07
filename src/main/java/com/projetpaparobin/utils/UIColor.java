package com.projetpaparobin.utils;

import javafx.scene.paint.Color;

public enum UIColor {

	RED("Red", Color.RED), BLUE("Blue", Color.LIGHTSKYBLUE), YELLOW("Yellow", Color.YELLOW), GREEN("Green", Color.LIGHTGREEN);
	
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
		if(color.equals(RED.getColor())) {
			return RED;
		} else if(color.equals(BLUE.getColor())) {
			return BLUE;
		} else if(color.equals(YELLOW.getColor())) {
			return YELLOW;
		} else {
			return GREEN;
		}
	}
	
}
