package com.agrigov.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrigov.model.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long>{

}