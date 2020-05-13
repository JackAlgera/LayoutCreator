package com.projetpaparobin.objects.extinguishers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Extinguisher {

	private Point extinguisherPos, textAreaPos;
	private DoubleProperty textAreaSize, radius;
	
	private StringProperty number, extinguisherType, protectionType, brand, local;
	private BooleanProperty isNew;
	private IntegerProperty fabricationYear;
	private UIColor color;

	@JsonBackReference
	private Zone zone;

	public Extinguisher() {
	}

	public Extinguisher(Point extinguisherPos, String number, String extinguisherType, String protectionType, String brand,
			String local, Boolean isNew, Integer fabricationYear, UIColor color, Zone zone) {
		this.extinguisherPos = extinguisherPos;
		setNumber(number);
		setExtinguisherType(extinguisherType);
		setProtectionType(protectionType);
		setBrand(brand);
		setLocal(local);
		setIsNew(isNew);
		setFabricationYear(fabricationYear);
		this.color = color;
		this.zone = zone;
	}

	public StringProperty numberProperty() {
		if(number == null) {
			number = new SimpleStringProperty();
		}
		return number;	
	}
	public StringProperty extinguisherTypeProperty() {
		if(extinguisherType == null) {
			extinguisherType = new SimpleStringProperty();
		}
		return extinguisherType;	
	}
	public StringProperty protectionTypeProperty() {
		if(protectionType == null) {
			protectionType = new SimpleStringProperty();
		}
		return protectionType;	
	}
	public StringProperty brandProperty() {
		if(brand == null) {
			brand = new SimpleStringProperty();
		}
		return brand;	
	}
	public StringProperty localProperty() {
		if(local == null) {
			local = new SimpleStringProperty();
		}
		return local;	
	}
	public BooleanProperty isNewProperty() {
		if(isNew == null) {
			isNew = new SimpleBooleanProperty();
		}
		return isNew;	
	}
	public IntegerProperty fabricationYearProperty() {
		if(fabricationYear == null) {
			fabricationYear = new SimpleIntegerProperty();
		}
		return fabricationYear;	
	}
	public DoubleProperty textAreaSizeProperty() {
		if(textAreaSize == null) {
			textAreaSize = new SimpleDoubleProperty();
		}
		return textAreaSize;	
	}
	public DoubleProperty radiusProperty() {
		if(radius == null) {
			radius = new SimpleDoubleProperty();
		}
		return radius;	
	}

	public Point getExtinguisherPos() {
		return extinguisherPos;
	}

	public void setExtinguisherPos(Point extinguisherPos) {
		this.extinguisherPos = extinguisherPos;
	}

	public Point getTextAreaPos() {
		return textAreaPos;
	}

	public void setTextAreaPos(Point textAreaPos) {
		this.textAreaPos = textAreaPos;
	}

	public Double getTextAreaSize() {
		return textAreaSizeProperty().get();
	}

	public void setTextAreaSize(Double textAreaSize) {
		textAreaSizeProperty().setValue(textAreaSize);
	}

	public Double getRadius() {
		return radiusProperty().get();
	}

	public void setRadius(Double radius) {
		radiusProperty().setValue(radius);;
	}

	public String getNumber() {
		return numberProperty().get();
	}

	public void setNumber(String number) {
		numberProperty().setValue(number);
	}

	public String getExtinguisherType() {
		return extinguisherTypeProperty().get();
	}

	public void setExtinguisherType(String extinguisherType) {
		extinguisherTypeProperty().setValue(extinguisherType);
	}

	public String getProtectionType() {
		return protectionTypeProperty().get();
	}

	public void setProtectionType(String protectionType) {
		protectionTypeProperty().setValue(protectionType);
	}

	public String getBrand() {
		return brandProperty().get();
	}

	public void setBrand(String brand) {
		brandProperty().setValue(brand);
	}

	public String getLocal() {
		return localProperty().get();
	}

	public void setLocal(String local) {
		localProperty().setValue(local);
	}

	public Boolean getIsNew() {
		return isNewProperty().get();
	}

	public void setIsNew(Boolean isNew) {
		isNewProperty().setValue(isNew);
	}

	public Integer getFabricationYear() {
		return fabricationYearProperty().get();
	}

	public void setFabricationYear(Integer fabricationYear) {
		fabricationYearProperty().setValue(fabricationYear);
	}

	public UIColor getColor() {
		return color;
	}

	public void setColor(UIColor color) {
		this.color = color;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	@JsonIgnore
	public String getZoneDisplayText() {
		return zone.getId().getDefaultAreaName();
	}
	@JsonIgnore
	public String getDisplayText() {
		return extinguisherTypeProperty().get() + "/" + protectionTypeProperty().get();
	}
	@JsonIgnore
	public void addID(ExtinguisherID id) {
		setNumber(id.getNumber());
		setExtinguisherType(id.getExtinguisherType());
		setProtectionType(id.getProtectionType());
		setBrand(id.getBrand());
		setLocal(id.getLocal());
		setIsNew(id.getIsNew());
		setFabricationYear(id.getFabricationYear());
		setColor(id.getColor());
	}
}
