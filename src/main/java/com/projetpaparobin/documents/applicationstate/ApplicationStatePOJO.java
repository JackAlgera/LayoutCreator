package com.projetpaparobin.documents.applicationstate;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.projetpaparobin.objects.zones.Zone;

@JsonRootName(value = "ApplicationState")
public class ApplicationStatePOJO {

	private ArrayList<Zone> zones;

	public ApplicationStatePOJO() {
		this.zones = new ArrayList<Zone>();
	}
	
	public ApplicationStatePOJO(ArrayList<Zone> zones) {
		this.zones = zones;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}
	
}
