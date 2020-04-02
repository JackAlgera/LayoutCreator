package com.projetpaparobin.zones;

public class Identifiant {
	
	private String areaType;
	private int areaNumber;
	private String activityType;
	private int areaSize;
	
	public Identifiant(String areaType, int areaNumber, String activityType, int areaSize) {
		this.areaType = areaType;
		this.areaNumber = areaNumber;
		this.activityType = activityType;
		this.areaSize = areaSize;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	
	public int getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(int areaNumber) {
		this.areaNumber = areaNumber;
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
