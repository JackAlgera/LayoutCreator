package com.projetpaparobin.objects.extinguishers;

public enum EProtectionType {

	PG("PG"), PC("PC"), PIP("PIP");

	private String type;

	private EProtectionType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

	public String getType() {
		return type;
	}

	public static EProtectionType getEnum(String value) {
		for (EProtectionType type : values()) {
			if (type.getType().equalsIgnoreCase(value)) {
				return type;
			}
		}
		return PG;
	}

}
