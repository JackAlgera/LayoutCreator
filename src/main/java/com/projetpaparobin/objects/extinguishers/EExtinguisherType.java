package com.projetpaparobin.objects.extinguishers;

import com.projetpaparobin.utils.UIColor;

public enum EExtinguisherType {

	/* Red */
	C2("C2", UIColor.RED), 
	C5("C5", UIColor.RED), 
	C10("C10", UIColor.RED), 
	C20("C20", UIColor.RED), 
	
	/* Blue */
	E6A("E6A", UIColor.BLUE), 
	E9A("E9A", UIColor.BLUE), 
	E6AEVT("E6AEVT", UIColor.BLUE), 
	E9AEVT("E9AEVT", UIColor.BLUE), 
	E6AEVP("E6AEVP", UIColor.BLUE), 
	AL6S_30("AL6S-30", UIColor.BLUE), 
	AL9S_30("AL9S-30", UIColor.BLUE), 
	E6AEVF("E6AEVF", UIColor.BLUE), 
	AL6F("AL6F", UIColor.BLUE), 
	E45A("E45A", UIColor.BLUE), 
	E45A2T("E45A2T", UIColor.BLUE),
	
	/* Yellow */
	P6P("P6P", UIColor.YELLOW), 
	P9P("P9P", UIColor.YELLOW), 
	P6("P6", UIColor.YELLOW), 
	P9("P9", UIColor.YELLOW), 
	P25P("P25P", UIColor.YELLOW), 
	P25("P25", UIColor.YELLOW), 
	P50P("P50P", UIColor.YELLOW), 
	P50("P50", UIColor.YELLOW), 
	
	/* Other */
	OTHER("OTHER", UIColor.GREEN);
	
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
	
	public static EExtinguisherType getDefaultExtinguisherType() {
		return E6A;
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
