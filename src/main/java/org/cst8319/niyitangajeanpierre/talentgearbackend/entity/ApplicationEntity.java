package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

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
public class ApplicationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    // References an id field in JobEntity
    private JobEntity job;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private UserEntity applicant;  // User with Role.JOB_SEEKER

    @JoinColumn(name = "application_date")
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    // Enum for Application Status
    public enum ApplicationStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        DRAFT,
        SUBMITTED
    }
}
