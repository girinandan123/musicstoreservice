package com.cx.uioc.dto;

import javax.validation.constraints.NotNull;

import com.cx.uioc.domain.AuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class AuditDTO {
	/**
	 * Constants
	 */
	/** Minimum Identifier of music */
	public final static int MIN_MUSIC_ID = 1;
	/**
	 * Properties
	 */
	/** Music id */
	
	@NotNull(message = "id of audit music requires not be blank")
	private long id;
	
	/** Reject reason */
	@JsonProperty("reject_reason")	
	private String rejectReason;
	
	public AuditEntity toEntity() {
		AuditEntity entity = new AuditEntity();
		entity.setId(id);
		if (rejectReason != null) {
			entity.setRejectReason(rejectReason);
		}
		return entity;
	}
	
	public AuditDTO(AuditEntity entity) {
		id = entity.getId();
		rejectReason = entity.getRejectReason();
	}
	
	public AuditDTO() {}
	
	public long getId() {
		return id;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
}
