package com.railway.ticketing.railway_reservation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.railway.ticketing.railway_reservation.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    @Lazy // Add @Lazy annotation to break the circular dependency
    private UserService userService;

    // Password encoder bean (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider using the UserService
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // Set the UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Set the password encoder
        return authProvider;
    }

    // Configuring the AuthenticationManagerBuilder to use DaoAuthenticationProvider
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    // Configuring the security filters, authorization rules, login, and logout
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity (not recommended for production)
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/register", "/login").permitAll() // Permit public URLs
                .anyRequest().authenticated() // Other requests require authentication
            )
            .formLogin((form) -> form
                .loginPage("/login") // Custom login page
                .permitAll() // Allow everyone to see the login page
                .defaultSuccessUrl("/dashboard", true) // Redirect to the dashboard on successful login
            )
            .logout((logout) -> logout
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                .permitAll()
            );

        return http.build();
    }
}