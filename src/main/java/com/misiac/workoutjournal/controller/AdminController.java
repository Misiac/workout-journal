package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import com.misiac.workoutjournal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/api/admin")

public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //TODO ADD TOKEN VALIDATION
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

    @PostMapping("/exercise")
    public ResponseEntity<String> addExercise(@RequestBody AdminCreateExerciseRequest adminExerciseRequest) throws Exception {

        adminService.addExercise(adminExerciseRequest);
        return new ResponseEntity<>("Exercise created successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/exercise/{exerciseId}/equipment-categories/{categoryName}")
    public ResponseEntity<String> bindEquipmentCategory
            (@PathVariable Long exerciseId, @PathVariable String categoryName) throws Exception {
        adminService.bindEquipmentCategory(exerciseId, categoryName);
        return new ResponseEntity<>("Equipment category successfully bound to the exercise", HttpStatus.OK);
    }

    @DeleteMapping("/exercise/{exerciseId}/equipment-categories/{categoryName}")
    public ResponseEntity<String> unbindEquipmentCategory(@PathVariable Long exerciseId, @PathVariable String categoryName) throws Exception {
        adminService.unbindEquipmentCategory(exerciseId, categoryName);
        return new ResponseEntity<>("Equipment category successfully unbound from the exercise", HttpStatus.OK);
    }

    @PatchMapping("/exercise/{exerciseId}/muscle-categories/{categoryName}")
    public ResponseEntity<String> bindMuscleCategory(@PathVariable Long exerciseId, @PathVariable String categoryName, @RequestParam boolean isPrimary) throws Exception {
        adminService.bindMuscleCategory(exerciseId, categoryName, isPrimary);
        return new ResponseEntity<>("Muscle category successfully bound to the exercise", HttpStatus.OK);
    }

    @DeleteMapping("/exercise/{exerciseId}/muscle-categories/{categoryName}")
    public ResponseEntity<String> unbindMuscleCategory(@PathVariable Long exerciseId, @PathVariable String categoryName) throws Exception {
        adminService.unbindMuscleCategory(exerciseId, categoryName);
        return new ResponseEntity<>("Muscle category successfully unbound from the exercise", HttpStatus.OK);
    }

}
