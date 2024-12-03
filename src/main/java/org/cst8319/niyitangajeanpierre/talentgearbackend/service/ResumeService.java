package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.ResumeEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.repository.ResumeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeEntity uploadResume(UserEntity user, String fileName, String fileUrl) {
        ResumeEntity resume = new ResumeEntity();
        resume.setUser(user);
        resume.setFileName(fileName);
        resume.setFileUrl(fileUrl);
        resume.setUploadTime(LocalDateTime.now());
        return resumeRepository.save(resume);
    }

    public Optional<ResumeEntity> findResumeByUser(UserEntity user) {
        return resumeRepository.findByUser(user);
    }

    public void deleteResume(Long resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}
