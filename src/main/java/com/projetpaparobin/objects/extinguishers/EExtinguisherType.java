package com.projetpaparobin.objects.extinguishers;

import com.projetpaparobin.utils.UIColor;

import javafx.scene.paint.Color;

public enum EExtinguisherType {

	/* Red */
	C2("C2", UIColor.RED.getColor()), 
	C5("C5", UIColor.RED.getColor()), 
	C10("C10", UIColor.RED.getColor()), 
	C20("C20", UIColor.RED.getColor()), 
	
	/* Blue */
	E6A("E6A", UIColor.BLUE.getColor()), E9A("E9A", UIColor.BLUE.getColor()), E6AEVT("E6AEVT", UIColor.BLUE.getColor()), 
	E9AEVT("E9AEVT", UIColor.BLUE.getColor()), E6AEVP("E6AEVP", UIColor.BLUE.getColor()), AL6S_30("AL6S-30", UIColor.BLUE.getColor()), 
	AL9S_30("AL9S-30", UIColor.BLUE.getColor()), E6AEVF("E6AEVF", UIColor.BLUE.getColor()), AL6F("AL6F", UIColor.BLUE.getColor()), 
	E45A("E45A", UIColor.BLUE.getColor()), E45A2T("E45A2T", UIColor.BLUE.getColor()),
	
	/* Yellow */
	P6P("P6P", UIColor.YELLOW.getColor()), P9P("P9P", UIColor.YELLOW.getColor()), P6("P6", UIColor.YELLOW.getColor()), 
	P9("P9", UIColor.YELLOW.getColor()), P25P("P25P", UIColor.YELLOW.getColor()), P25("P25", UIColor.YELLOW.getColor()), 
	P50P("P50P", UIColor.YELLOW.getColor()), P50("P50", UIColor.YELLOW.getColor()), 
	
	/* Other */
	OTHER("OTHER", UIColor.GREEN.getColor());
	
	private Color color;
	private String name;
	
	private EExtinguisherType(String name, Color color) {
		this.color = color;
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public static String getDefaultExtinguisherType() {
		return C20.getName();
	}
	
	public static EExtinguisherType getEnum(String value) {
        for(EExtinguisherType type : values()) {
            if(type.getName().equalsIgnoreCase(value)) {
            	return type;
            }        	
        }

        return OTHER;
    }
	
}
