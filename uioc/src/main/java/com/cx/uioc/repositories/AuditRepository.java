package com.cx.uioc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cx.uioc.domain.AuditEntity;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

}
