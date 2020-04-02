package com.projetpaparobin.zones;

public enum EZoneType {

	TERTIAIRE("Tertiaire"), INDUSTRIELLE("Industrielle");
	
	private String type;
	
	private EZoneType(String type) {
		this.type=type;
	}
	
}
