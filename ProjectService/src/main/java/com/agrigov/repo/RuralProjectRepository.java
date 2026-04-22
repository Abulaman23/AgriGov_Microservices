package com.agrigov.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrigov.model.RuralProject;

@Repository
public interface RuralProjectRepository extends JpaRepository<RuralProject, Long> {

}