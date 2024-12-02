package org.cst8319.niyitangajeanpierre.talentgearbackend.controller;

import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.ResumeEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.ResumeService;
import org.cst8319.niyitangajeanpierre.talentgearbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<ResumeEntity> uploadResume(
            @PathVariable Long userId,
            @RequestParam String fileName,
            @RequestParam String fileUrl) {
        UserEntity user = userService.getUserById(userId);
        ResumeEntity resume = resumeService.uploadResume(user, fileName, fileUrl);
        return ResponseEntity.ok(resume);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResumeEntity> getResume(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId);
        return resumeService.findResumeByUser(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long resumeId) {
        resumeService.deleteResume(resumeId);
        return ResponseEntity.noContent().build();
    }
}

