package org.cst8319.niyitangajeanpierre.talentgearbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication
public class TalentGearBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(TalentGearBackendApplication.class, args);
    }

}
