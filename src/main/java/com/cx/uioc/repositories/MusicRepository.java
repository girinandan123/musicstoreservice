package com.cx.uioc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cx.uioc.domain.MusicEntity;

public interface MusicRepository extends JpaRepository<MusicEntity, Long> {

	/** evaluate exists the entity with the title */
	boolean existsByTitle(String title);

	Optional<MusicEntity> findByTitle(String title);

}
