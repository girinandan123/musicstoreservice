package com.cx.uioc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.cx.uioc.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class SystemUserEntity {
	/**
	 * Properties
	 */
	/** user identifier */
	@Id
	private String id;
		
	/** user name */
	@Column(nullable = false)
	private String name;
	
	/** password */
	@Column(nullable = false)
	private String password;
	
	/** email */
	@Column(nullable = false)
	private String email;
	
	/** user role */
	@Column(nullable = false)
	@ElementCollection(targetClass = Role.class)
	private List<Role> roles;
	
		
}
