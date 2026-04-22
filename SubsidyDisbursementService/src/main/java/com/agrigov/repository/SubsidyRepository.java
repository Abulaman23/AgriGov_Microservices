package com.agrigov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agrigov.model.Subsidy;

public interface SubsidyRepository extends JpaRepository<Subsidy, Long> {

    boolean existsBySchemeIdAndFarmerId(Long schemeId, Long farmerId);
}
