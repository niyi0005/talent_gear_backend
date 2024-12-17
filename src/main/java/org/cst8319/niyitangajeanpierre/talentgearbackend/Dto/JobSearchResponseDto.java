package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class JobSearchResponseDto {
    private String name;
    private String description;
    private String industry;
    private String location;
    private double salary;
    private String employerUsername;
}
