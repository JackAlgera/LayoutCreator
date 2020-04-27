package com.projetpaparobin.objects.zones;

public enum EUnits {

	Kg("Kg"), L("L"), m2("m²");
	
	private String type;
	
	private EUnits(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
	public static EUnits getEnum(String value) {
        for(EUnits type : values()) {
            if(type.getType().equalsIgnoreCase(value)) {
            	return type;
            }        	
        }

        return m2;
    }
	
}
