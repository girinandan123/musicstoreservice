package com.cx.uioc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cx.uioc.domain.MusicEntity;
import com.cx.uioc.domain.enums.AuditStatus;
import com.cx.uioc.dto.MusicStoreDTO;
import com.cx.uioc.exception.NotExistedException;
import com.cx.uioc.repositories.MusicRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class StoreService {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(StoreService.class);

	/**
	 * Members
	 */

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private MusicRepository musicRepository;
	
	/**
	 * Get all musics where the audit status is pending
	 */
	public List<MusicStoreDTO> getMusics() throws Exception {
		logger.info(StoreService.class.getName() + ".getMusics [({})]");
	
		String strQuery = "SELECT e FROM MusicEntity e WHERE e.status=?1";
		Query query = entityManager.createQuery(strQuery);
		query.setParameter(1, AuditStatus.PASSED);
		
		@SuppressWarnings("unchecked")
		List<MusicEntity> entities = query.getResultList();
		
		List<MusicStoreDTO> dtos = new ArrayList<>();
		
		for(MusicEntity entity : entities) {
			MusicStoreDTO dto = new MusicStoreDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	/**
	 * Get music URL by id
	 */
	public Map<String, Object> getMusicUrlById(long id) throws Exception {
		logger.info(StoreService.class.getName(), ".getMusicUrlById [id({})]", id);
		
		Map<String, Object> response = new HashMap<>();
		
		boolean existed = musicRepository.existsById(id);
		if (!existed) {
			throw new NotExistedException("music of id to get " + id);
		}
		
		MusicEntity entity = musicRepository.findById(id).get();
		
		response.put("contents_url", entity.getContentsUrl());
		
		return response;
	}
	

}
