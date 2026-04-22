package com.agrigov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

}
