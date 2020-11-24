package com.cx.uioc.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cx.uioc.domain.SystemUserEntity;
import com.cx.uioc.domain.enums.Role;
import com.cx.uioc.dto.SystemUserDTO;
import com.cx.uioc.exception.AlreadyExistedException;
import com.cx.uioc.exception.NotExistedException;
import com.cx.uioc.exception.WrongArgumentException;
import com.cx.uioc.repositories.SystemUserRepository;


/**
 * SystemUserService class - Service for system user management 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserService {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(SystemUserService.class);

	/**
	 * Members
	 */	
	/** Password encoder */
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	
	/**
	 * Find a system user by user id
	 */
	public SystemUserEntity findOneByUserId(long userId) throws Exception {
		logger.info(SystemUserService.class.getName() + ".findOneByUserId [userId({})]", userId);
		
		return systemUserRepository.findById(userId).get();
	}

	/**
	 * Add a new system user
	 */
	public String addNewSystemUser(SystemUserDTO systemUserDTO) throws Exception {
		logger.info(SystemUserService.class.getName() + ".addNewSystemUser [systemUserDTO({})]", systemUserDTO);
		
        // Find if the system user with same user id already existed at repository
        String userId = systemUserDTO.getUserId();
        boolean existed = systemUserRepository.existsById(userId);
        if (existed) {
        	throw new AlreadyExistedException("system user with user id " + userId);
        }        
        
        existed = systemUserRepository.existsByEmail(systemUserDTO.getEmail());
        if (existed) {
        	throw new AlreadyExistedException("system user with user email " + systemUserDTO.getEmail());
        }
        
        // Insert the new system user into repository
        SystemUserEntity systemUser = systemUserDTO.toEntity();
        
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        systemUser.setRoles(roles);
        
        if (systemUser.getPassword() != null) {
			systemUser.setPassword(bCryptPasswordEncoder.encode(systemUser.getPassword()));	// Encode password
		}        
        
        
        systemUserRepository.save(systemUser);
        
        // Find the system user just added
        systemUser = systemUserRepository.findOneById(userId).get();
        
        // Success
        return systemUser.getId();
	}



	/**
	 * Add a new super administrator
	 */
	public void addSuperAdmin(SystemUserEntity systemUserEntity) {
		logger.info(SystemUserService.class.getName() + ".addSuperAdmin [systemUserEntity({})]", systemUserEntity);
		
		// Evaluate that system user exists with same id
		boolean existed = systemUserRepository.existsById(systemUserEntity.getId());
		if (existed) {
			logger.info("already existed super admin");
			return;
		}
		systemUserRepository.save(systemUserEntity);
	}

	/**
	 * Get all system users
	 */
	public List<SystemUserDTO> getSystemUsers() throws Exception {
	
	
		List<SystemUserEntity> systemUsers = systemUserRepository.findAll();
		List<SystemUserDTO> dtos = new ArrayList<>();
		
		for(SystemUserEntity systemUser : systemUsers) {
			List<Role> roles = systemUser.getRoles();
			if (roles.contains(Role.ADMIN)) {
				SystemUserDTO dto = new SystemUserDTO(systemUser);
				dtos.add(dto);
			}
			
		}
		return dtos;
	}
	
	/**
	 * Update system user by identifier 
	 */
	public SystemUserDTO updateSystemUser(String userId, SystemUserDTO systemUserDTO) throws Exception {
		logger.info(SystemUserService.class.getName() + ".updateSystemUser [userId({}), systemUserDTO({})]", userId, systemUserDTO);
		
		// Check if the identifier matched with system user's one
		if (!userId.equals(systemUserDTO.getUserId())) {
			throw new WrongArgumentException("id of system user to update " + systemUserDTO.getUserId());
		}
		
		// Check if the system user exists
		boolean existed = systemUserRepository.existsById(userId);
		if (!existed) {
			throw new NotExistedException("system user of id to update " + userId);						
		}
		
		SystemUserEntity systemUser = systemUserDTO.toEntity();
		
		// Evaluate password
		String password = systemUser.getPassword();
		if (password != null) {	// In the case user password updated
			systemUser.setPassword(bCryptPasswordEncoder.encode(password));	// Encode password
		} else if (password == null || password == "") {	// In the case user password not updated
			SystemUserEntity user = systemUserRepository.findOneById(userId).get();
			// Set the user password original value
			systemUser.setPassword(user.getPassword());
		}
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		systemUser.setRoles(roles);
			
		systemUserRepository.deleteById(userId);
		systemUserRepository.save(systemUser);
		

		// Find the system user just updated
		SystemUserEntity updated = systemUserRepository.findOneById(userId).get();

		// Success
		return new SystemUserDTO(updated);
	}

	/**
	 * Delete system user by identifier
	 */
	public boolean deleteSystemUser(String userId) throws Exception {
		logger.info(SystemUserService.class.getName() + ".deleteSystemUser [userId({})]", userId);
        
        // Delete the system user by identifier
        systemUserRepository.deleteById(userId);

        // Success
        return true;
	}	
	
}
