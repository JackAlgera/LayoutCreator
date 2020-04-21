package com.projetpaparobin.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpaparobin.objects.zones.EActivityType;

import javafx.scene.paint.Color;

public enum UIColor {
	RED(	"Rouge", 	new Color(Color.RED.getRed(), 	Color.RED.getGreen(), 	Color.RED.getBlue(), 	0.5)),
	BLUE(	"Bleu", 	new Color(Color.BLUE.getRed(), 	Color.BLUE.getGreen(), 	Color.BLUE.getBlue(), 	0.5)),
	YELLOW(	"Jaune", 	new Color(Color.YELLOW.getRed(),Color.YELLOW.getGreen(),Color.YELLOW.getBlue(), 0.5)),
	GREEN(	"Vert", 	new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 	0.5)),
	BROWN(	"Marron", 	new Color(Color.BROWN.getRed(), Color.BROWN.getGreen(), Color.BROWN.getBlue(), 	0.5)),
	ORANGE(	"Orange", 	new Color(Color.ORANGE.getRed(),Color.ORANGE.getGreen(),Color.ORANGE.getBlue(), 0.5)),
	BLACK(	"Noir", 	new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 	1.0)),
	WHITE(	"Blanche", 	new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 	1.0)),
	
	LIGHTBLUE(	"Bleu clair", 	new Color(Color.LIGHTBLUE.getRed(), Color.LIGHTBLUE.getGreen(), Color.LIGHTBLUE.getBlue(), 	1.0)),
	LIGHTRED(	"Roug clair", 	new Color(Color.RED.getRed(), 		Color.RED.getGreen(), 		Color.RED.getBlue(), 		1.0)),
	LIGHTYELLOW("Jaune clair",	new Color(Color.YELLOW.getRed(), 	Color.YELLOW.getGreen(), 	Color.YELLOW.getBlue(), 	1.0)),
	LIGHTGREEN(	"Vert clair", 	new Color(Color.LIGHTGREEN.getRed(),Color.LIGHTGREEN.getGreen(),Color.LIGHTGREEN.getBlue(), 1.0));

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
	
	public static UIColor getEnum(String colorName) {
        for(UIColor type : values()) {
            if(type.getColorName().equalsIgnoreCase(colorName)) {
            	return type;
            }        	
        }

        return BLUE;
    }
	
}
