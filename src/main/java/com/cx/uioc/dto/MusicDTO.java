package com.cx.uioc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class MusicDTO {
	/**
	 * Constants
	 */
	/** Minimum Identifier of UserEntity */
	public final static int MIN_USER_ID = 1;
	/** Minimum music title length */
	public final static int MIN_TITLE_LENGTH = 3;
	/** Minimum music description length */
	public final static int MIN_DESC_LENGTH  = 5;
	/**
	 * Properties
	 */
	/** Music id */
	@Getter
	private long id;
	
	/** Music title */
	@Getter
	@NotNull(message = "title of music requires not be blank")
	@Size(min = MIN_TITLE_LENGTH, message = "length of music title requires equal to or greater than " + MIN_TITLE_LENGTH)
	private String title;
	
	/** Author */
	@Getter	
	private String author;
	
	/** Price */
	@Getter
	@NotNull(message = "price of music requires not be blank")
	private Double price;
	
	/** Music description */
	@Getter
	@JsonProperty("description")
	private String description;
	
	/** Music copyright */
	@Getter
	private String copyright;
	
	/** Music contents size */
	@JsonProperty("contents_size")
	@Getter
	@NotNull(message = "contents size requires not be blank")
	private Long contentsSize;
	
	/** Music contents URL */
	@JsonProperty("contents_url")
	@Getter
	@NotNull(message = "contents url requires not be blank")
	private String contentsUrl;
	
	/** Music audit status */
	@Getter
	private String status;
	
	/**
	 * Initialization
	 */
	public MusicDTO(MusicEntity entity) throws Exception {
		id = entity.getId();
		title = entity.getTitle();
		author = entity.getAuthor();
		contentsSize = entity.getContentsSize();
		contentsUrl = entity.getContentsUrl();
		copyright = entity.getCopyright();
		description = entity.getDescription();
		price = entity.getPrice();
		status = entity.getStatus().getValue();
	}

	/**
	 * Methods
	 */
	/** Convert the DTO to entity */
	public MusicEntity toEntity() throws Exception {
		MusicEntity entity = new MusicEntity();
		
		entity.setAuthor(author);
		entity.setTitle(title);
		entity.setContentsSize(contentsSize);
		entity.setContentsUrl(contentsUrl);
		entity.setCopyright(copyright);
		entity.setDescription(description);
		entity.setPrice(price);
		
		
		return entity;
	}
}
