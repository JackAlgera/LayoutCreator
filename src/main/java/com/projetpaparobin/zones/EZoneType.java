package com.projetpaparobin.zones;

public enum EZoneType {

	TERTIAIRE("Tertiaire"), INDUSTRIELLE("Industrielle");
	
	private String type;
	
	private EZoneType(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	
	public static EZoneType getEnum(String value) {
        for(EZoneType type : values()) {
            if(type.getType().equalsIgnoreCase(value)) {
            	return type;
            }        	
        }

        return TERTIAIRE;
    }
}
