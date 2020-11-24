package com.cx.uioc.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cx.uioc.domain.MusicEntity;
import com.cx.uioc.domain.enums.AuditStatus;
import com.cx.uioc.dto.MusicDTO;
import com.cx.uioc.exception.AlreadyExistedException;
import com.cx.uioc.repositories.DatabaseFileRepository;
import com.cx.uioc.repositories.MusicRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class MusicService {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(MusicService.class);

	/**
	 * Members
	 */

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private MusicRepository musicRepository;
	
	@Autowired
	private DatabaseFileRepository fileRepository;

	/**
	 * Add a new music
	 */
	public long addNewMusic(MusicDTO dto) throws Exception {
		logger.info(MusicService.class.getName() + ".addNewMusic [MusicDTO({})]", dto);
		
        // Find if the music with same title already existed at repository
        String title = dto.getTitle();
        boolean existed = musicRepository.existsByTitle(title);
        if (existed) {
        	throw new AlreadyExistedException("music with title " + title);
        }        
               
        
        // Insert the new music into repository
        MusicEntity entity = dto.toEntity();          
        // When added new music, set the status to pending
        entity.setStatus(AuditStatus.PENDING);
        
        musicRepository.save(entity);
        
        // Find the music just added
        entity = musicRepository.findByTitle(title).get();
        
        // Success
        return entity.getId();
	}

	/**
	 * Get all musics
	 */
	public List<MusicDTO> getMusics() throws Exception {
	
	
		List<MusicEntity> entities = musicRepository.findAll();
		List<MusicDTO> dtos = new ArrayList<>();
		
		for(MusicEntity entity : entities) {
			MusicDTO dto = new MusicDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	

	/**
	 * Delete the music by identifier
	 */
	public boolean deleteMusic(long id) throws Exception {
		logger.info(MusicService.class.getName() + ".deleteMusic [id({})]", id);
        
		MusicEntity entity = musicRepository.findById(id).get();
		String fileUrl = entity.getContentsUrl();
		// Split the contents_url to get the file id
		String[] items = fileUrl.split("/");
		//System.out.println(items[items.length - 2]);
		// Get the file id
		String fileId = items[items.length - 2];
		
        // Delete the music by identifier
        musicRepository.deleteById(id);
        
        // Delete the corresponding file data
        fileRepository.deleteById(fileId);
        
        // Success
        return true;
	}

}