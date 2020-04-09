package com.projetpaparobin.objects.zones;

public class ZoneID {

	private String areaName;
	private int areaNumber;
	private EAreaType areaType;
	private EActivityType activityType;
	private int areaSize;
	
	public ZoneID(String areaName, EAreaType areaType, int areaNumber, EActivityType activityType, int areaSize) {
		this.areaNumber = areaNumber;
		this.areaType = areaType;
		this.activityType = activityType;
		this.areaSize = areaSize;
		this.areaName = (areaName.isBlank()) ? getDisplayText() : areaName;
	}
			
	public String getDisplayText() {
		return areaType.toString() + areaNumber + "-" + getActivityTypeAbbreviation() + "-" + areaSize + "m²";
	}
	
	public String getActivityTypeAbbreviation() {
		switch (activityType) {
		case INDUSTRIELLE:
			return "I";
		case TERTIAIRE:
			return "T";
		default:
			return "";
		}
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}	
	
}
