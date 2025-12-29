package com.example.demo.controller;

import com.example.demo.entity.Staff;
import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/staff")
public class AdminStaffController {

    private final StaffService staffService;

    // ================= LIST =================
    @GetMapping
    public String staffPage(Model model) {
        model.addAttribute("staff", staffService.getAllStaff());
        return "admin-staff";
    }

    // ================= ADD FORM =================
    @GetMapping("/add")
    public String addStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "admin-add-staff";
    }

    // ================= SAVE =================
    @PostMapping("/add")
    public String saveStaff(@ModelAttribute Staff staff) {
        staffService.saveStaff(staff);
        return "redirect:/admin/staff";
    }

    // ================= EDIT FORM =================
@GetMapping("/edit/{id}")
public String editStaffForm(@PathVariable Long id, Model model) {
    Staff staff = staffService.getStaffById(id);
    model.addAttribute("staff", staff);
    return "admin-edit-staff";
}

@PostMapping("/update/{id}")
public String updateStaff(
        @PathVariable Long id,
        @ModelAttribute Staff staff) {

    staff.setId(id);
    staffService.saveStaff(staff);
    return "redirect:/admin/staff";
}


    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/admin/staff";
    }
}
