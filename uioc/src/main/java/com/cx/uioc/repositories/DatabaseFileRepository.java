package com.cx.uioc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cx.uioc.domain.DatabaseFile;


/**
 * DatabaseFileRepository Class - Repository for file
 */
@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {
	/**
	 * Methods
	 */
	/** Find the file by id and name from repository */
	Optional<DatabaseFile> findByFileIdAndFileName(String fileId, String fileName);
	
	// Check if the file with the same contents existed
	boolean existsByData(byte[] bytes);
}