package com.cx.uioc.dto;

import com.cx.uioc.domain.MusicEntity;
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
public class MusicStoreDTO {
	/** Music id */
	@Getter
	private long id;
	/** Music title */
	@Getter
	private String title;
	/** Music author */
	@Getter
	private String author;
	/** Music price */
	@Getter
	private double price;
	/** Music description */
	@Getter
	private String description;
	/** Music copyright */
	@Getter
	private String copyright;
	/** Music contents size */
	@Getter
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
}
