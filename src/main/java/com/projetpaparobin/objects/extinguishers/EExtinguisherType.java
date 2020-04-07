package com.projetpaparobin.objects.extinguishers;

import javafx.scene.paint.Color;

public enum EExtinguisherType {

	/* Red */
	C2("C2", Color.RED), C6("C6", Color.RED), C10("C10", Color.RED), C20("C20", Color.RED), 
	/* Red */
	E6A("E6A", Color.BLUE), E9A("E9A", Color.BLUE), E6AEVT("E6AEVT", Color.BLUE), E9AEVT("E9AEVT", Color.BLUE), E6AEVP("E6AEVP", Color.BLUE), 
	AL6S_30("AL6S-30", Color.BLUE), AL9S_30("AL9S-30", Color.BLUE), E6AEVF("E6AEVF", Color.BLUE), AL6F("AL6F", Color.BLUE), E45A("E45A", Color.BLUE), E45A2T("E45A2T", Color.BLUE),
	/* Red */
	P6P("P6P", Color.RED), P9P("P9P", Color.RED), P6("P6", Color.RED), P9("P9", Color.RED), P25P("P25P", Color.RED), P25("P25", Color.RED), P50P("P50P", Color.RED), P50("P50", Color.RED), 
	/* Other */
	OTHER("OTHER", Color.GREEN);
	
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
	
	public static EExtinguisherType getEnum(String value) {
        for(EExtinguisherType type : values()) {
            if(type.getName().equalsIgnoreCase(value)) {
            	return type;
            }        	
        }

        return OTHER;
    }
	
}
