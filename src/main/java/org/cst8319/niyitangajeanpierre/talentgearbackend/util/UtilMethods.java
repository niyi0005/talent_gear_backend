package org.cst8319.niyitangajeanpierre.talentgearbackend.util;


import lombok.RequiredArgsConstructor;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
}
