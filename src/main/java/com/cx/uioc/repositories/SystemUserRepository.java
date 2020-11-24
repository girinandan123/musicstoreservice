package com.cx.uioc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cx.uioc.domain.SystemUserEntity;



@Repository
public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Long> {

	boolean existsById(String userId);

	Optional<SystemUserEntity> findOneByName(String name);

	Optional<SystemUserEntity> findOneById(String userId);

	void deleteById(String userId);

	boolean existsByEmail(String email);
	
	
}
