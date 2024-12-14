package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Setter
@Getter
public class UserUpdateDto {
    private String email;
    private String password;
    private Set<String> roles;
    private String phoneNumber;
    private String address;
    private String bio;
    private String website;
    private String resumeUrl;
}
