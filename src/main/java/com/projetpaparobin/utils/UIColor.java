package com.projetpaparobin.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javafx.scene.paint.Color;

public enum UIColor {
	LIGHTRED(	 "Rouge clair",  new Color(Color.RED.getRed(), 	   Color.RED.getGreen(), 	 Color.RED.getBlue(), 	  0.5)),
	LIGHTBLUE(	 "Bleu clair", 	 new Color(Color.BLUE.getRed(),    Color.BLUE.getGreen(),  	 Color.BLUE.getBlue(), 	  0.5)),
	LIGHTYELLOW( "Jaune clair",  new Color(Color.YELLOW.getRed(),  Color.YELLOW.getGreen(),  Color.YELLOW.getBlue(),  0.5)),
	LIGHTGREEN(	 "Vert clair", 	 new Color(Color.GREEN.getRed(),   Color.GREEN.getGreen(),   Color.GREEN.getBlue(),   0.5)),
	LIGHTGREY(	 "Gris clair", 	 new Color(Color.GREY.getRed(),    Color.GREY.getGreen(),    Color.GREY.getBlue(), 	  0.5)),
	LIGHTMAGENTA("Magenta clair",new Color(Color.MAGENTA.getRed(), Color.MAGENTA.getGreen(), Color.MAGENTA.getBlue(), 0.5)),
	LIGHTCYAN(   "Cyan clair",   new Color(Color.CYAN.getRed(),    Color.CYAN.getGreen(),    Color.CYAN.getBlue(),    0.5)),
	LIGHTBROWN(  "Marron clair", new Color(Color.BROWN.getRed(),   Color.BROWN.getGreen(),   Color.BROWN.getBlue(),   0.5)),
	
	BLACK(		"Noir", 		new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 	1.0)),
	WHITE(		"Blanche", 		new Color(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue(), 	1.0)),
	
	ORANGE(	"Orange", 	new Color(255.0 / 255, 	153.0 / 255, 	36.0 / 255, 	1.0)),	
	BLUE(	"Bleu", 	new Color(74.0 / 255, 	168.0 / 255, 	214.0 / 255, 	1.0)),
	RED(	"Rouge", 	new Color(192.0 / 255, 	97.0 / 255, 	94.0 / 255, 	1.0)),
	YELLOW("Jaune",		new Color(255.0 / 255, 	216.0 / 255, 	72.0 / 255, 	1.0)),
	GREEN(	"Vert", 	new Color(161.0 / 255, 	217.0 / 255, 	61.0 / 255, 	1.0)),
	GREY(   "Gris",		new Color(110.0 / 255, 	110.0 / 255, 	110.0 / 255, 	1.0)),
	MAGENTA("Magenta",	new Color(255.0 / 255, 	0.0 / 255,  	220.0 / 255, 	1.0)),
	CYAN(   "Cyan",	    new Color(0.0 / 255, 	183.0 / 255,  	235.0 / 255, 	1.0)),
	BROWN(  "Marron",	new Color(100.0 / 255, 	50.0 / 255,  	0.0 / 255, 	1.0));
	
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
