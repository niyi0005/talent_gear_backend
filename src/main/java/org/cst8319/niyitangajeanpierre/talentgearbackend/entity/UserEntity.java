package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")

public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Lob
    private String bio;
    private String resume_url; // URL or file path for the resume
    private String address;
    private String phoneNumber;
    private String website;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "employer")
    // JobEntity has a employer field that references UserEntity foreign key.
    private List<JobEntity> postedJobs;

    @OneToMany(mappedBy = "applicant")
    // ApplicationEntity has an applicant field that references UserEntity foreign key.
    private List<ApplicationEntity> applications;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    // ResumeEntity has a user field that references UserEntity foreign key.
    private ResumeEntity resume;

    @OneToMany(mappedBy = "recipient")
    private List<MessageEntity> receivedMessages;

    @OneToMany(mappedBy = "sender")
    private List<MessageEntity> sentMessages;

    //Enum for user roles
    public enum Role {
        JOB_SEEKER,
        EMPLOYER
    }

}