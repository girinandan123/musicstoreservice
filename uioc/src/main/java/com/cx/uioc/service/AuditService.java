package com.cx.uioc.service;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cx.uioc.domain.AuditEntity;
import com.cx.uioc.domain.MusicEntity;
import com.cx.uioc.domain.enums.AuditStatus;
import com.cx.uioc.dto.AuditDTO;
import com.cx.uioc.dto.MusicDTO;
import com.cx.uioc.exception.NotExistedException;
import com.cx.uioc.repositories.AuditRepository;
import com.cx.uioc.repositories.MusicRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuditService {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(AuditService.class);

	/**
	 * Members
	 */

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private MusicRepository musicRepository;
	
	@Autowired
	private AuditRepository auditRepository;
	
	

	/**
	 * Set the music audit status to passed
	 */
	public long setAuditToPassed(AuditDTO dto) throws Exception {
		logger.info(AuditService.class.getName() + ".setAuditToPassed [AuditDTO({})]", dto);
		
        // Find if the music with the id existed at repository
        Long id = dto.getId();
        boolean existed = musicRepository.existsById(id);
        if (!existed) {
        	throw new NotExistedException("music with id " + id);
        }        
               
        MusicEntity entity = musicRepository.findById(dto.getId()).get();
        // Set the audit status to passed
        String strQuery = "UPDATE MusicEntity e SET e.status = ?1 WHERE e.id=?2";
        Query query = entityManager.createQuery(strQuery);
        query.setParameter(1, AuditStatus.PASSED);
        query.setParameter(2, entity.getId());
        
        query.executeUpdate();
               
        
        // Success
        return entity.getId();
	}

	/**
	 * Set the music audit status to rejected
	 */
	public long setAuditToRejected(AuditDTO dto) throws Exception {
		logger.info(AuditService.class.getName() + ".setAuditToRejected [AuditDTO({})]", dto);
		
        // Find if the music with the id existed at repository
        Long id = dto.getId();
        boolean existed = musicRepository.existsById(id);
        if (!existed) {
        	throw new NotExistedException("music with id " + id);
        }        
               
        MusicEntity entity = musicRepository.findById(dto.getId()).get();
        // Set the audit status to rejected
        String strQuery = "UPDATE MusicEntity e SET e.status = ?1 WHERE e.id=?2";
        Query query = entityManager.createQuery(strQuery);
        query.setParameter(1, AuditStatus.REJECTED);
        query.setParameter(2, entity.getId());
        
        query.executeUpdate();
        
        // Record the rejected reason to repository
        AuditEntity auditEntity = dto.toEntity();
        auditRepository.save(auditEntity);
        
        // Success
        return entity.getId();
	}

	/**
	 * Get all musics where the pending status is pending	 
	 */
	public List<MusicDTO> getPendingMusics() throws Exception {
		
		List<MusicDTO> dtos = new LinkedList<>();
		
		String strQuery = "SELECT e FROM MusicEntity e WHERE e.status=?1";
		Query query = entityManager.createQuery(strQuery);
		query.setParameter(1, AuditStatus.PENDING);
		
		@SuppressWarnings("unchecked")
		List<MusicEntity> entities = query.getResultList();
		
		for(MusicEntity entity : entities) {
			MusicDTO dto = new MusicDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

}
