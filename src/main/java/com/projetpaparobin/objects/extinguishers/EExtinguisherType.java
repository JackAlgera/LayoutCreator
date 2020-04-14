package com.projetpaparobin.objects.extinguishers;

import com.projetpaparobin.utils.UIColor;

public enum EExtinguisherType {

	/* Red */
	C2("C2", UIColor.LIGHTRED), 
	C5("C5", UIColor.LIGHTRED), 
	C10("C10", UIColor.LIGHTRED), 
	C20("C20", UIColor.LIGHTRED), 
	
	/* Blue */
	E6A("E6A", UIColor.LIGHTBLUE), E9A("E9A", UIColor.LIGHTBLUE), E6AEVT("E6AEVT", UIColor.LIGHTBLUE), 
	E9AEVT("E9AEVT", UIColor.LIGHTBLUE), E6AEVP("E6AEVP", UIColor.LIGHTBLUE), AL6S_30("AL6S-30", UIColor.LIGHTBLUE), 
	AL9S_30("AL9S-30", UIColor.LIGHTBLUE), E6AEVF("E6AEVF", UIColor.LIGHTBLUE), AL6F("AL6F", UIColor.LIGHTBLUE), 
	E45A("E45A", UIColor.LIGHTBLUE), E45A2T("E45A2T", UIColor.LIGHTBLUE),
	
	/* Yellow */
	P6P("P6P", UIColor.LIGHTYELLOW), P9P("P9P", UIColor.LIGHTYELLOW), P6("P6", UIColor.LIGHTYELLOW), 
	P9("P9", UIColor.LIGHTYELLOW), P25P("P25P", UIColor.LIGHTYELLOW), P25("P25", UIColor.LIGHTYELLOW), 
	P50P("P50P", UIColor.LIGHTYELLOW), P50("P50", UIColor.LIGHTYELLOW), 
	
	/* Other */
	OTHER("OTHER", UIColor.LIGHTGREEN);
	
	private UIColor color;
	private String name;
	
	private EExtinguisherType(String name, UIColor color) {
		this.color = color;
		this.name = name;
	}
	
	public UIColor getColor() {
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
