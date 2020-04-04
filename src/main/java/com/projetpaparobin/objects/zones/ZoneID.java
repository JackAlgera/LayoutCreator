package com.projetpaparobin.objects.zones;

public class ZoneID {
	
	private String areaType;
	private int areaNumber;
	private EZoneType activityType;
	private int areaSize;
	
	public ZoneID(String areaType, int areaNumber, String activityType, int areaSize) {
		this.areaType = areaType;
		this.areaNumber = areaNumber;
		this.activityType = EZoneType.getEnum(activityType);
		this.areaSize = areaSize;
	}

	public ZoneID(String areaType, int areaNumber, EZoneType activityType, int areaSize) {
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

	public EZoneType getActivityType() {
		return activityType;
	}

	public void setActivityType(EZoneType activityType) {
		this.activityType = activityType;
	}

	public int getAreaSize() {
		return areaSize;
	}

	public void setAreaSize(int areaSize) {
		this.areaSize = areaSize;
	}	
	
}
