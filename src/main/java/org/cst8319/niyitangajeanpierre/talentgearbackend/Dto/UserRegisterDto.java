package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;

import lombok.Getter;
import lombok.Setter;
import org.cst8319.niyitangajeanpierre.talentgearbackend.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Setter
@Getter
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
    private String roles;
    private String phoneNumber;
    private String address;
    private String bio;
    private String website;
    private String resumeUrl;
}
