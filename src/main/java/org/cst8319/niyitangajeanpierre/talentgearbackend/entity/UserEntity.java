package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "password_reset_token_expiration_date")
    private LocalDateTime passwordResetTokenExpirationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @Lob
    private String bio;
    @Column(name = "resume_url")
    private String resumeUrl; // URL or file path for the resume
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String website;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    // JobEntity has a employer field that references UserEntity foreign key.
    private List<JobEntity> postedJobs;

    @OneToMany(mappedBy = "applicantId", fetch = FetchType.LAZY)
    // ApplicationEntity has an applicantId field that references UserEntity foreign key.
    private List<JobApplicationEntity> applications;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // ResumeEntity has a user field that references UserEntity foreign key.
    private ResumeEntity resume;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    private List<MessageEntity> receivedMessages;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<MessageEntity> sentMessages;


}
