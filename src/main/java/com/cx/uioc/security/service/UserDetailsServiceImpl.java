package com.cx.uioc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cx.uioc.domain.SystemUserEntity;
import com.cx.uioc.repositories.SystemUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private SystemUserRepository systemUserRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException { 
		
		SystemUserEntity user = null;
		
		user = systemUserRepository.findOneById(userId).get();
		if (user == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		return UserDetailsImpl.build(user);
	}

}
