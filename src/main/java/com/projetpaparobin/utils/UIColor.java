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
	
}
