package com.projetpaparobin.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.paint.Color;

public enum UIColor {
	RED("Red", new Color(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), 0.5)),
	BLUE("Blue", new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 0.5)),
	YELLOW("Yellow", new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), 0.5)),
	GREEN("Green", new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 0.5)),
	BROWN("Brown", new Color(Color.BROWN.getRed(), Color.BROWN.getGreen(), Color.BROWN.getBlue(), 0.5)),
	ORANGE("Orange", new Color(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 0.5)),
	BLACK("Black", new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 1.0)),
	WHITE("White", new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 1.0)),
	LIGHTBLUE("Blue", new Color(Color.LIGHTBLUE.getRed(), Color.LIGHTBLUE.getGreen(), Color.LIGHTBLUE.getBlue(), 0.5)),
	LIGHTRED("Red", new Color(Color.MEDIUMVIOLETRED.getRed(), Color.MEDIUMVIOLETRED.getGreen(), Color.MEDIUMVIOLETRED.getBlue(), 0.5)),
	LIGHTYELLOW("Yellow", new Color(Color.LIGHTYELLOW.getRed(), Color.LIGHTYELLOW.getGreen(), Color.LIGHTYELLOW.getBlue(), 0.5)),
	LIGHTGREEN("Green", new Color(Color.LIGHTGREEN.getRed(), Color.LIGHTGREEN.getGreen(), Color.LIGHTGREEN.getBlue(), 0.5));

	private String colorName;
	
	@JsonIgnore
	private Color color;
	
	private UIColor(String colorName, Color color) {
		this.colorName = colorName;
		this.color = color;
	}

	@JsonIgnore
	public Color getColor() {
		return color;
	}
	
	public String getColorName() {
		return colorName;
	}

	@Override
	public String toString() {
		return colorName;
	}

	@JsonIgnore
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
