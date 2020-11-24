package com.cx.uioc.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PurchaseRequest {
	/**
	 * Constants
	 */
	/** Minimum Identifier of UserEntity */
	public final static long MIN_MUSIC_ID = 1L;
	/**
	 * Properties
	 */
	/** Music id */
	@Getter
	@NotNull(message = "id of music requires not be blank")
	@Min(value = MIN_MUSIC_ID, message = "id of music requires equal to or greater than " + MIN_MUSIC_ID)
	private long id;
	
	/** Purchaser email address */
	@Getter
	@NotNull(message = "email of purchaser requires not be blank")	
	private String email;
}
