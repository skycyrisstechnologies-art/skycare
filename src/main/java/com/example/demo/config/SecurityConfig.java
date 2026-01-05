package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    // ✅ Explicit constructor (NO Lombok issues)
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // ================= PASSWORD ENCODER =================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ================= AUTH PROVIDER =================
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ================= SECURITY FILTER CHAIN =================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔥 Disable CSRF (required for chatbot / ajax)
            .csrf(csrf -> csrf.disable())

            // 🔐 AUTHORIZATION RULES
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/home",
                        "/login",
                        "/api/chat/**",
                        "/patient/**",
                        "/patient/register",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()

                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/doctor/**").hasRole("DOCTOR")
                .requestMatchers("/staff/**")
                    .hasAnyRole("RECEPTIONIST", "NURSE", "PHARMACIST")

                .anyRequest().authenticated()
            )

            // 🔐 LOGIN CONFIG (ROLE-BASED REDIRECT)
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")

                .successHandler((request, response, authentication) -> {

                    var authorities = authentication.getAuthorities();

                    if (authorities.stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/admin/dashboard");
                        return;
                    }

                    if (authorities.stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"))) {
                        response.sendRedirect("/doctor/dashboard");
                        return;
                    }

                    if (authorities.stream()
                            .anyMatch(a ->
                                    a.getAuthority().equals("ROLE_RECEPTIONIST")
                                 || a.getAuthority().equals("ROLE_NURSE")
                                 || a.getAuthority().equals("ROLE_PHARMACIST"))) {
                        response.sendRedirect("/staff/dashboard");
                        return;
                    }

                    // fallback
                    response.sendRedirect("/home");
                })

                .failureUrl("/login?error")
                .permitAll()
            )

            // 🔐 LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // 🔐 SESSION MANAGEMENT
            .sessionManagement(session -> session
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            )

            // 🔥 REQUIRED: PREVENT LOGIN PAGE CACHING (BACK BUTTON FIX)
            .headers(headers -> headers
                .cacheControl(Customizer.withDefaults())
            )

            // 🔐 AUTH PROVIDER
            .authenticationProvider(authProvider());

        return http.build();
    }
}
