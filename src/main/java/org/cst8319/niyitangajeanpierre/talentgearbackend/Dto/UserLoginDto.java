package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserLoginDto {
    private String username;
    private String password;
}
