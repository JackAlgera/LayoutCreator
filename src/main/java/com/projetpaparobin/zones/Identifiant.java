package com.projetpaparobin.zones;

public class Identifiant {
	
	private String areaType;
	private String activityType;
	private int areaSize;
	
	public Identifiant(String areaType, String activityType, int areaSize) {
		this.areaType = areaType;
		this.activityType = activityType;
		this.areaSize = areaSize;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public int getAreaSize() {
		return areaSize;
	}

	public void setAreaSize(int areaSize) {
		this.areaSize = areaSize;
	}
		
}
