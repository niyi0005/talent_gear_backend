package org.cst8319.niyitangajeanpierre.talentgearbackend.Dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class JobDto {
    private Long id;
    private String name;
    private String description;
    private String industry;
    private String location;
    private double salary;
    private Long employerId;
}
