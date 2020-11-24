package com.cx.uioc.domain.enums;

public enum AuditStatus {
	PENDING("PENDING"), PASSED("PASSED"), REJECTED("REJECTED");

	private String value;

	AuditStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
