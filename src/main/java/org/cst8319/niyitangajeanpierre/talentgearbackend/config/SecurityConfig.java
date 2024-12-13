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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Lazy
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(@Lazy final JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/forgot-password", "/reset-password/**").permitAll()
                    // user should be authenticated to edit their profile
                .requestMatchers(HttpMethod.PUT, "/api/users/me").authenticated()
                   // POST, PUT, DELETE methods on jobs exclusive to employers
                .requestMatchers(HttpMethod.POST, "/api/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.PUT, "/api/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.DELETE, "/api/jobs/**").hasRole("EMPLOYER")
                .requestMatchers(HttpMethod.GET, "/api/jobs/search/**").permitAll()
                    // POST, PUT, DELETE methods on resumes exclusive to job_seekers
                .requestMatchers(HttpMethod.POST, "/api/resumes/**").hasRole("JOB_SEEKER")
                .requestMatchers(HttpMethod.PUT, "/api/resumes/**").hasRole("JOB_SEEKER")
                .requestMatchers(HttpMethod.DELETE, "/api/resumes/**").hasRole("JOB_SEEKER")
                .requestMatchers(HttpMethod.GET, "/api/resumes/**").hasAnyRole("JOB_SEEKER", "EMPLOYER")
                    // POST, PUT, DELETE methods on job application exclusive to job_seekers
                .requestMatchers(HttpMethod.POST,"/api/applications/**").hasRole("JOB_SEEKER")
                .requestMatchers(HttpMethod.PUT,"/api/applications/**").hasRole("JOB_SEEKER")
                .requestMatchers(HttpMethod.DELETE,"/api/applications/**").hasRole("JOB_SEEKER")
                .requestMatchers(HttpMethod.GET,"/api/applications/**").hasAnyRole("JOB_SEEKER", "EMPLOYER")
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Updated CORS configuration
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
