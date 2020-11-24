package com.cx.uioc.domain.enums;

public enum Role {

	SUPERADMIN("SUPERADMIN"), ADMIN("ADMIN"), USER("USER");

	private String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}