package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String bio;
    private String website;
    private String resumeUrl;
}
