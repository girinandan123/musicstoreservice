package com.cx.uioc.dto;

import com.cx.uioc.domain.MusicEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicStoreDTO {
	/** Music id */
	private long id;
	/** Music title */
	private String title;
	/** Music author */
	private String author;
	/** Music price */
	private double price;
	/** Music description */
	private String description;
	/** Music copyright */
	private String copyright;
	/** Music contents size */
	@JsonProperty("contents_size")
	private long contentsSize;
	
	/** Initialization */
	public MusicStoreDTO(MusicEntity entity) {
		id = entity.getId();
		price = entity.getPrice();
		contentsSize = entity.getContentsSize();
		title = entity.getTitle();
		author = entity.getAuthor();
		description = entity.getDescription();
		copyright = entity.getCopyright();
		
	}
	
	public MusicStoreDTO() {}
}
