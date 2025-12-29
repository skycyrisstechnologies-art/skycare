package com.example.demo.service;

import com.example.demo.entity.Staff;

import java.util.List;

public interface StaffService {

    List<Staff> getAllStaff();

    Staff getStaffById(Long id);

    void saveStaff(Staff staff);

    void deleteStaff(Long id);
}
