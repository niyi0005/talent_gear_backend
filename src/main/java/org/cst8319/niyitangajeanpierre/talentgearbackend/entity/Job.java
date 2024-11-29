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

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String industry;
    private String location;
    private Double salary;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User employer;  // Assuming employer is a User with Role.EMPLOYER

    @OneToMany(mappedBy = "job")
    private List<Application> applications;
}
