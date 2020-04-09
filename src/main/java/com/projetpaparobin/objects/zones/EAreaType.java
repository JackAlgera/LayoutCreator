package com.projetpaparobin.objects.zones;

public enum EAreaType {

	ZB("ZB"), ZIP("ZIP");
	
	private String type;
	
	private EAreaType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
	public static EAreaType getEnum(String value) {
        for(EAreaType type : values()) {
            if(type.getType().equalsIgnoreCase(value)) {
            	return type;
            }        	
        }

        return ZB;
    }
	
}
