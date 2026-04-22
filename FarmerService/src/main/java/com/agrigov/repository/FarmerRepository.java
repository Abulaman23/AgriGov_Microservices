package com.agrigov.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.agrigov.model.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

	// Using DSL Syntax
	// Find farmers by status
	List<Farmer> findByStatus(String status);

	// Find farmers by name
	List<Farmer> findByName(String name);

	// Find farmers by gender
	List<Farmer> findByGender(String gender);
}