package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="applications")
public class JobApplicationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Column(name = "job_id")
    // References an id field in JobEntity
    private Long jobId;


    @Column(name = "applicant_id")
    private Long applicantId;  // User with Role.JOB_SEEKER

    @Lob
    private String resumeContent;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    // Enum for Application Status
    public enum ApplicationStatus {
        LIKED,
        PENDING,
        ACCEPTED,
        REJECTED,
        DRAFT,
        SUBMITTED
    }
}
