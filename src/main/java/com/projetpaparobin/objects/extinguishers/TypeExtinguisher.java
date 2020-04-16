package com.projetpaparobin.objects.extinguishers;

public class TypeExtinguisher {

	private String type;
	private int fabricationYear;
	private EProtectionType protectionType;
	
	public TypeExtinguisher(String type, int fabricationYear, EProtectionType protectionType) {
		this.type = type;
		this.fabricationYear = fabricationYear;
		this.protectionType = protectionType;
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
