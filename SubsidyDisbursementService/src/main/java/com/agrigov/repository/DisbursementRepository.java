package com.agrigov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.model.Disbursement;

public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

}
