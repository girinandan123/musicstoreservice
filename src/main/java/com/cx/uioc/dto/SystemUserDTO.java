package com.cx.uioc.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.cx.uioc.domain.SystemUserEntity;
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
public class SystemUserDTO {
	/**
	 * Constants
	 */
	/** Minimum Identifier of UserEntity */
	public final static int MIN_USER_ID = 1;
	/**
	 * Properties
	 */
	/** User identifier */
	@JsonProperty("id")
	@Getter
	@NotNull(message = "id of user requires not be blank")
	private String userId;
	
	/** User name */
	@Getter
	@NotNull(message = "name of user requires not be blank")
	private String name;
	
	/** Email */
	@Getter
	@NotNull(message = "email of user requires not be blank")
	private String email;
	
	/** Password */
	@Getter
	@NotNull(message = "password of user requires not be blank")
	private String password;
	
	/**
	 * Initialization
	 */
	public SystemUserDTO(SystemUserEntity systemUser) throws Exception {
		userId = systemUser.getId();
		name = systemUser.getName();
		email = systemUser.getEmail();
		password = systemUser.getPassword();
		
	}

	/**
	 * Methods
	 */
	/** Convert the DTO to entity */
	public SystemUserEntity toEntity() throws Exception {
		SystemUserEntity systemUser = new SystemUserEntity();
		
		systemUser.setId(userId);
		systemUser.setEmail(email);
		systemUser.setName(name);
		systemUser.setId(userId);
		systemUser.setPassword(password);
		
		return systemUser;
	}
}



