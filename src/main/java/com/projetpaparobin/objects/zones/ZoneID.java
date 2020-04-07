package com.projetpaparobin.objects.zones;

public class ZoneID {

	private int areaNumber;
	private EAreaType areaType;
	private EActivityType activityType;
	private int areaSize;
	
	public ZoneID(EAreaType areaType, int areaNumber, EActivityType activityType, int areaSize) {
		this.areaNumber = areaNumber;
		this.areaType = areaType;
		this.activityType = activityType;
		this.areaSize = areaSize;
	}
		
	public EAreaType getAreaType() {
		return areaType;
	}

	public void setAreaType(EAreaType areaType) {
		this.areaType = areaType;
	}
	
	public int getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(int areaNumber) {
		this.areaNumber = areaNumber;
	}

	public EActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(EActivityType activityType) {
		this.activityType = activityType;
	}

	public int getAreaSize() {
		return areaSize;
	}

	public void setAreaSize(int areaSize) {
		this.areaSize = areaSize;
	}	
	
}
