package com.cx.uioc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cx.uioc.domain.enums.AuditStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class MusicEntity {
	/**
	 * Properties
	 */
	/** user identifier */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
		
	/** title */
	@Column(nullable = false)
	private String title;
	
	/** author */
	@Column
	private String author;
	
	/** price */
	@Column(nullable = false)
	private Double price;
	
	/** description */
	@Column	
	private String description;
	
	/** copyright */
	@Column
	private String copyright;
	
	/** contents size */
	@Column(nullable = false)
	private Long contentsSize;
	
	/** contents URL */
	@Column(nullable = false)
	private String contentsUrl;
	
	/** music status - pending, passed, or rejected */
	@Column
	private AuditStatus status;
		
}
