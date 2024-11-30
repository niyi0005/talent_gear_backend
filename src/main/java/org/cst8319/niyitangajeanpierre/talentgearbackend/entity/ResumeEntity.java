package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeEntity {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity user;  // The resume belongs to one user

    private String fileName;  // File name of the resume (e.g., "resume.pdf")
    private String fileUrl;   // URL or path where the resume is stored
    private LocalDateTime uploadTime;

}

