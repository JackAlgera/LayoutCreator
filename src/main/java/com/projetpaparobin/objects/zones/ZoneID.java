package com.projetpaparobin.objects.zones;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.paint.Color;

public class ZoneID {

	private String areaName;
	private int areaNumber;
	private EAreaType areaType;
	private EActivityType activityType;
	private int areaSize;
	private EUnits units;
	private UIColor fillColor;
	@JsonIgnore
	private Color rimColor;

	public ZoneID() {
		this.fillColor = UIElements.DEFAULT_ZONE_CREATION_COLOR;
		this.rimColor = fillColor.getColor().darker();
	}

	public ZoneID(String areaName, EAreaType areaType, int areaNumber, EActivityType activityType, int areaSize,
			EUnits units, UIColor fillColor) {
		this.areaNumber = areaNumber;
		this.areaType = areaType;
		this.activityType = activityType;
		this.areaSize = areaSize;
		this.areaName = areaName;
		this.units = units;
		this.fillColor = fillColor;
		this.rimColor = fillColor.getColor().darker();
	}

	@JsonIgnore
	public String getDisplayText() {
		return areaType.toString() + areaNumber + "-" + getActivityTypeAbbreviation() + "-" + areaSize + "m²";
	}

	@JsonIgnore
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

	public UIColor getFillColor() {
		return fillColor;
	}

	public void setFillColor(UIColor fillColor) {
		if (fillColor == null) {
			this.fillColor = UIElements.getRandomColor();
		} else {
			this.fillColor = fillColor;
		}
		this.rimColor = fillColor.getColor().darker();
	}

	public Color getRimColor() {
		return rimColor;
	}

	public EUnits getUnits() {
		return units;
	}

	public void setUnits(EUnits units) {
		this.units = units;
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
		return (areaName.isBlank() ? getDefaultAreaName() : areaName);
	}

	@JsonIgnore
	public String getDefaultAreaName() {
		return areaType.toString() + areaNumber;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityType == null) ? 0 : activityType.hashCode());
		result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result + areaNumber;
		result = prime * result + areaSize;
		result = prime * result + ((areaType == null) ? 0 : areaType.hashCode());
		result = prime * result + ((fillColor == null) ? 0 : fillColor.hashCode());
		result = prime * result + ((rimColor == null) ? 0 : rimColor.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZoneID other = (ZoneID) obj;
		if (activityType != other.activityType)
			return false;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		if (areaNumber != other.areaNumber)
			return false;
		if (areaSize != other.areaSize)
			return false;
		if (areaType != other.areaType)
			return false;
		if (fillColor != other.fillColor)
			return false;
		if (rimColor == null) {
			if (other.rimColor != null)
				return false;
		} else if (!rimColor.equals(other.rimColor))
			return false;
		if (units != other.units)
			return false;
		return true;
	}

}
