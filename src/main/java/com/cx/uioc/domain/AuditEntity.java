package com.cx.uioc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reject_music_entity")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class AuditEntity {

	/** Music identifier */
	@Id
	private long id;
	
	/** Audit rejected reason */
	@Column
	private String rejectReason;
}
