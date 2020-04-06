package com.projetpaparobin.objects.extinguishers;

import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;

import javafx.scene.paint.Color;

public class Extinguisher {

	private ExtinguisherID id;
	private Point pos;
	private Zone zone;
	
	public Extinguisher() {
	}
	
	public Extinguisher(int number, String extinguisherType, String protectionType, Color color,
			Point pos, boolean isNew, Zone zone,int anneeMiseEnService,String marque) {
		id = new ExtinguisherID(number, extinguisherType, protectionType, anneeMiseEnService, marque, isNew, color);
		this.pos = pos;
		this.zone = zone;
	}
	
	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public String getDisplayText() {
		return id.getDisplayText();
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public void setId(ExtinguisherID id) {
		this.id = id;
	}
	
	public ExtinguisherID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
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
		Extinguisher other = (Extinguisher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}
	
}
