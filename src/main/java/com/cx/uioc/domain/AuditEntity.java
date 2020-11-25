package com.cx.uioc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "reject_music_entity")
@JsonIgnoreProperties(ignoreUnknown = true)

public class AuditEntity {

	/** Music identifier */
	@Id
	private long id;
	
	/** Audit rejected reason */
	@Column
	private String rejectReason;
	
	public AuditEntity() {
		
	}
	
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
