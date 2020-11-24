package com.cx.uioc.dto;

import javax.validation.constraints.NotNull;

import com.cx.uioc.domain.AuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
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
	@Getter
	@NotNull(message = "id of audit music requires not be blank")
	private long id;
	
	/** Reject reason */
	@JsonProperty("reject_reason")
	@Getter
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
}
