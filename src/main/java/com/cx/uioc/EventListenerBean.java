package com.cx.uioc;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cx.uioc.domain.SystemUserEntity;
import com.cx.uioc.domain.enums.Role;
import com.cx.uioc.service.SystemUserService;

/**
 * EventListenerBean class - Class for Spring boot startup logic 
 */
@Component
@PropertySource("classpath:application.properties")
public class EventListenerBean {
	/** Logger */
	private Logger logger = LogManager.getLogger(EventListenerBean.class);
			
	/** AuthService field */
    @Autowired
    private SystemUserService systemUserService;
    
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    
    
    @Autowired
    private Environment env;
    
    /** Method to register administrator info */
    @EventListener
    public void onApplicationEventRegisterAdministrator(ContextRefreshedEvent event) {
    	logger.info(EventListenerBean.class.getName() + ".onApplicationEventRegisterAdministrator[({event})]", event);
    	
        try {
        	
        	String defaultAdmin = env.getProperty("uioc.defaultAdmin");
        	String defaultAdminId = env.getProperty("uioc.defaultAdminId");
        	String defaultPassword = env.getProperty("uioc.defaultPassword");
			// Register administrator user data to repository
        	SystemUserEntity systemUserEntity = new SystemUserEntity();
        	systemUserEntity.setId(defaultAdminId);
        	systemUserEntity.setName(defaultAdmin);
        	systemUserEntity.setEmail("admin@123.com");
        	systemUserEntity.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
        	
        	List<Role> roles = new ArrayList<>();
        	roles.add(Role.SUPERADMIN);
        	systemUserEntity.setRoles(roles);
        	
        	systemUserService.addSuperAdmin(systemUserEntity);
			
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
