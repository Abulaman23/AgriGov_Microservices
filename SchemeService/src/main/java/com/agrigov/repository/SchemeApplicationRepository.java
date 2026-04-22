package com.agrigov.repository;

import com.agrigov.model.SchemeApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchemeApplicationRepository
        extends JpaRepository<SchemeApplication, Long> {

    // ✅ Single application (unique)
    Optional<SchemeApplication> findByApplicationID(Long applicationID);

    // ✅ MULTIPLE applications for one scheme (CRITICAL FIX)
    List<SchemeApplication> findByPolicyscheme_SchemeID(Long schemeID);
    Optional<SchemeApplication> findByPolicyscheme_SchemeIDAndFarmerID(Long schemeID, Long farmerID);
}
