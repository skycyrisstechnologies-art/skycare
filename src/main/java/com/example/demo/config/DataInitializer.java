package com.example.demo.config;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            // Create roles
            Role adminRole = roleRepo.findByName("ADMIN")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ADMIN");
                        return roleRepo.save(r);
                    });

            Role doctorRole = roleRepo.findByName("DOCTOR")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("DOCTOR");
                        return roleRepo.save(r);
                    });

            Role receptionistRole = roleRepo.findByName("RECEPTIONIST")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("RECEPTIONIST");
                        return roleRepo.save(r);
                    });

            // Create Admin User
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                admin.setRoles(Set.of(adminRole));
                userRepo.save(admin);
            }

            // Create Doctor User
            if (userRepo.findByUsername("doc1").isEmpty()) {
                User doc = new User();
                doc.setUsername("doc1");
                doc.setPassword(passwordEncoder.encode("doc123"));
                doc.setEnabled(true);
                doc.setRoles(Set.of(doctorRole));
                userRepo.save(doc);
            }

            // Create Receptionist User
            if (userRepo.findByUsername("reception1").isEmpty()) {
                User rec = new User();
                rec.setUsername("reception1");
                rec.setPassword(passwordEncoder.encode("rec123"));
                rec.setEnabled(true);
                rec.setRoles(Set.of(receptionistRole));
                userRepo.save(rec);
            }
        };
    }
}
