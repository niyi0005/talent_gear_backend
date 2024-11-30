package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="jobs")

public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String industry;
    private String location;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "employerId") // The employer who posted the job
    private UserEntity employer;

    @OneToMany(mappedBy = "job") // Refer to the 'job' field in ApplicationEntity
    private List<ApplicationEntity> applications;

    // Track the number of applications for this job
    @Transient  // This field is not stored in the database, but can be computed or tracked in the service layer
    private Integer applicationsCount;

    public Integer getApplicationsCount() {
        return applications == null ? 0 : applications.size();  // Default to 0 if applications are null
    }
}
