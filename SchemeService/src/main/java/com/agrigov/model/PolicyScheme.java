package com.agrigov.model;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.agrigov.enums.SchemeStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Table(name = "PolicyScheme")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyScheme {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SchemeID")
    private Long schemeID;
 
    @Column(name = "Title", nullable = false, length = 150)
    private String title;
 
    @Column(name = "Description", length = 1000)
    private String description;
 
    @Column(name = "StartDate")
    private LocalDate startDate;
 
    @Column(name = "EndDate")
    private LocalDate endDate;
 
    @Column(name = "Budget")
    private Double budget;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 30)
    private SchemeStatus status;
 
   
    @OneToMany(mappedBy = "policyscheme")
    private Set<SchemeApplication> applications = new HashSet<>();
    
    //@OneToMany(
    /*	    mappedBy = "policyScheme",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    	//private List<ComplianceRecord> complianceRecords = new ArrayList<>();
    	///
    
*/
    
}