package org.example.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    CustomUserDetailService customUserDetailService;
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
    };

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.formLogin().disable()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/trainee").permitAll()
                                .requestMatchers(HttpMethod.POST, "/trainer").permitAll()
                                .requestMatchers(HttpMethod.POST, "/training").permitAll()
                                .requestMatchers("/swagger-ui/index.html").permitAll()
                                .anyRequest().authenticated()
                );
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }
}
