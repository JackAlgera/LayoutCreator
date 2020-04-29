package com.projetpaparobin.objects.zones;

public enum EActivityType {

	TERTIAIRE("Tertiaire"), INDUSTRIELLE("Industrielle");

	private String type;

	private EActivityType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}

	public static EActivityType getEnum(String value) {
		for (EActivityType type : values()) {
			if (type.getType().equalsIgnoreCase(value)) {
				return type;
			}
		}

		return TERTIAIRE;
	}
}
