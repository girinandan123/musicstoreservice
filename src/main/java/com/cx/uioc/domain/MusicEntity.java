package com.cx.uioc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cx.uioc.domain.enums.AuditStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

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
	
	public MusicEntity() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	public long getContentsSize() {
		return contentsSize;
	}
	
	public void setContentsSize(long contentsSize) {
		this.contentsSize = contentsSize;
	}
	
	public String getContentsUrl() {
		return contentsUrl;
	}
	
	public void setContentsUrl(String contentsUrl) {
		this.contentsUrl = contentsUrl;
	}
	
	public AuditStatus getStatus() {
		return status;
	}
	
	public void setStatus(AuditStatus status) {
		this.status = status;
	}
}
