package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/api/admin")

public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/muscle-category")
    public ResponseEntity<String> addMuscleGroupCategory(@RequestParam String name) throws Exception {

        adminService.addMuscleGroupCategory(name);
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/equipment-category")
    public ResponseEntity<String> addEquipmentCategory(@RequestParam String name) throws Exception {

        adminService.addEquipmentCategory(name);
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }
}
