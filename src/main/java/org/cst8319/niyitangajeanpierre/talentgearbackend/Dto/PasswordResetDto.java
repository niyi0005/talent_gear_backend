package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {
    private String newPassword;
    private String confirmNewPassword;
}
