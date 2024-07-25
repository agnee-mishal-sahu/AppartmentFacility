package com.appartment.facilities.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appartment.facilities.security.classes.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/auth/**").permitAll() // Permit all access to authentication endpoints
            .requestMatchers("/v1/manager/register/**").permitAll() 
            .requestMatchers("/v1/resident/register/**").permitAll()
            .requestMatchers("/v1/manager/**").hasAuthority("Manager")
            .requestMatchers("/v1/resident/**").hasAnyAuthority("Manager","Resident")
            .requestMatchers("/v1/facility/getall/**").hasAnyAuthority("Manager","Resident")
            .requestMatchers("/v1/facility/byid/**").hasAnyAuthority("Manager","Resident")
            .requestMatchers("/v1/facility/**").hasAuthority("Manager")
            .requestMatchers("/v1/booking/create/**").hasAnyAuthority("Manager","Resident")
            .requestMatchers("/v1/booking/byid/**").hasAnyAuthority("Manager","Resident")
            .requestMatchers("/v1/booking/cancel/**").hasAnyAuthority("Manager","Resident")
            .requestMatchers("/v1/booking/**").hasAuthority("Manager")
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
