package com.projetpaparobin.documents.output;

import com.projetpaparobin.objects.extinguishers.EProtectionType;
import com.projetpaparobin.objects.extinguishers.Extinguisher;

public class TypeExtinguisher {

	private String type;
	private int fabricationYear;
	private EProtectionType protectionType;
	private String local;
	
	public TypeExtinguisher(Extinguisher ex) {
		this.type = ex.getProtectionType();
		this.fabricationYear = ex.getFabricationYear();
		this.protectionType = EProtectionType.getEnum(ex.getProtectionType());
		this.local = ex.getLocal();
	}
	
	public String getLocal() {
		return local;
	}
	
	public String getType() {
		return type;
	}
	
	public int getFabricationYear() {
		return fabricationYear;
	}

	public EProtectionType getProtectionType() {
		return protectionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fabricationYear;
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((protectionType == null) ? 0 : protectionType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		TypeExtinguisher other = (TypeExtinguisher) obj;
		if (fabricationYear != other.fabricationYear)
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (protectionType != other.protectionType)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
