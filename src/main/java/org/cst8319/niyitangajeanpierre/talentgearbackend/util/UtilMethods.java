package org.cst8319.niyitangajeanpierre.talentgearbackend.util;


import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.Dto.JobSearchResponseDto;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.JobEntity;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UtilMethods {
    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("User is not authenticated");
        }
        return authentication.getName();
    }

    public boolean hasJobSeekerRole(UserEntity user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("JOB_SEEKER"));
    }

    public boolean hasEmployerRole(UserEntity user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("EMPLOYER"));
    }

    public static JobSearchResponseDto mapToDto(JobEntity jobEntity) {
        JobSearchResponseDto dto = new JobSearchResponseDto();
        dto.setName(jobEntity.getName());
        dto.setDescription(jobEntity.getDescription());
        dto.setIndustry(jobEntity.getIndustry());
        dto.setLocation(jobEntity.getLocation());
        dto.setSalary(jobEntity.getSalary());
        dto.setEmployerUsername(jobEntity.getEmployer().getUsername());
        return dto;
    }

    public List<JobSearchResponseDto> mapToDtoList(List<JobEntity> jobEntities) {
        return jobEntities.stream()
                .map(UtilMethods::mapToDto)
                .collect(Collectors.toList());
    }
}
