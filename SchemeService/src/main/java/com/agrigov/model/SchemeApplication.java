package com.agrigov.model;
 
import java.time.LocalDate;

import com.agrigov.enums.SchemeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
 
@Entity
@Table(
    name = "SchemeApplication",
    indexes = {
        @Index(name = "idx_scheme_application_farmer", columnList = "FarmerID"),
        @Index(name = "idx_scheme_application_scheme", columnList = "SchemeID")
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeApplication {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplicationID")
    private Long applicationID;
 
    
//    @ManyToOne
//    @JoinColumn(name = "FarmerID", nullable = false)
//    private Farmer farmer;
    
    private Long farmerID;
 
  
    @ManyToOne
    @JoinColumn(name = "SchemeID", nullable = false)
    private PolicyScheme policyscheme;
 
    @Column(name = "SubmittedDate")
    private LocalDate submittedDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 30)
    private SchemeStatus status;


	private Long complianceId;
	private String result;
}