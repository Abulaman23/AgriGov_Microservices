package com.agrigov.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.model.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long> {


	
}
