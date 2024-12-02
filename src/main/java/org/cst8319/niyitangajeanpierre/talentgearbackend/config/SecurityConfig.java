package org.cst8319.niyitangajeanpierre.talentgearbackend.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Lazy
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(@Lazy final JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    // Configure HttpSecurity for Spring Boot 3.4
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/register", "/api/users").permitAll()
                        .requestMatchers("/api/users/profile/{id}/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/jobs/**").hasAnyRole("EMPLOYER", "JOB_SEEKER")
                        .requestMatchers("/api/jobs/**").hasRole("EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/api.resumes/**").hasAnyRole("EMPLOYER", "JOB_SEEKER")
                        .requestMatchers("/api/resumes/**").authenticated()
                        .requestMatchers("/api/messages/**").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions->exceptions
                        .authenticationEntryPoint((request, response, authException) ->{
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        } )
                )
                //Ensure that the request is authenticated before proceeding further in the application.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }





    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
