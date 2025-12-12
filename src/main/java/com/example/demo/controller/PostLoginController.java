package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostLoginController {

    private final UserRepository userRepository;

    @GetMapping("/postLogin")
    public String postLogin(Authentication authentication) {

        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "redirect:/login?error";
        }

        User user = optionalUser.get();

        boolean isAdmin = hasRole(user, "ADMIN");
        boolean isDoctor = hasRole(user, "DOCTOR");
        boolean isReceptionist = hasRole(user, "RECEPTIONIST");
        boolean isNurse = hasRole(user, "NURSE");
        boolean isPharmacist = hasRole(user, "PHARMACIST");

        if (isAdmin) {
            return "redirect:/admin/dashboard";
        }

        if (isDoctor) {
            return "redirect:/doctor/dashboard";
        }

        if (isReceptionist || isNurse || isPharmacist) {
            return "redirect:/staff/dashboard";
        }

        return "redirect:/login?error";
    }

    private boolean hasRole(User user, String roleName) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
