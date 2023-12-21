package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import com.misiac.workoutjournal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.misiac.workoutjournal.util.AdminValidator.validateAdmin;

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
    public ResponseEntity<String> addMuscleGroupCategory(@RequestHeader(value = "Authorization") String token, @RequestParam String name) throws Exception {
        if (validateAdmin(token)) {
            adminService.addMuscleGroupCategory(name);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } else {
            throw new Exception("Admin privileges required");
        }
    }

    @PostMapping("/equipment-category")
    public ResponseEntity<String> addEquipmentCategory(@RequestHeader(value = "Authorization") String token, @RequestParam String name) throws Exception {
        if (validateAdmin(token)) {
            adminService.addEquipmentCategory(name);
            return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
        } else {
            throw new Exception("Admin privileges required");
        }
    }

    @PostMapping("/exercise")
    public ResponseEntity<String> addExercise(@RequestHeader(value = "Authorization") String token,
                                              @RequestBody AdminCreateExerciseRequest adminExerciseRequest) throws Exception {
        if (validateAdmin(token)) {
            adminService.addExercise(adminExerciseRequest);
            return new ResponseEntity<>("Exercise created successfully", HttpStatus.CREATED);
        } else {
            throw new Exception("Admin privileges required");
        }
    }

    @PatchMapping("/exercise/{exerciseId}/equipment-categories/{categoryName}")
    public ResponseEntity<String> bindEquipmentCategory
            (@RequestHeader(value = "Authorization") String token, @PathVariable Long exerciseId,
             @PathVariable String categoryName) throws Exception {
        if (validateAdmin(token)) {
            adminService.bindEquipmentCategory(exerciseId, categoryName);
            return new ResponseEntity<>("Equipment category successfully bound to the exercise", HttpStatus.OK);
        } else {
            throw new Exception("Admin privileges required");
        }
    }

    @DeleteMapping("/exercise/{exerciseId}/equipment-categories/{categoryName}")
    public ResponseEntity<String> unbindEquipmentCategory(@RequestHeader(value = "Authorization") String token,
                                                          @PathVariable Long exerciseId, @PathVariable String categoryName) throws Exception {
        if (validateAdmin(token)) {
            adminService.unbindEquipmentCategory(exerciseId, categoryName);
            return new ResponseEntity<>("Equipment category successfully unbound from the exercise", HttpStatus.OK);
        } else {
            throw new Exception("Admin privileges required");
        }
    }

    @PatchMapping("/exercise/{exerciseId}/muscle-categories/{categoryName}")
    public ResponseEntity<String> bindMuscleCategory(@RequestHeader(value = "Authorization") String token,
                                                     @PathVariable Long exerciseId, @PathVariable String categoryName,
                                                     @RequestParam boolean isPrimary) throws Exception {
        if (validateAdmin(token)) {
            adminService.bindMuscleCategory(exerciseId, categoryName, isPrimary);
            return new ResponseEntity<>("Muscle category successfully bound to the exercise", HttpStatus.OK);
        } else {
            throw new Exception("Admin privileges required");
        }
    }

    @DeleteMapping("/exercise/{exerciseId}/muscle-categories/{categoryName}")
    public ResponseEntity<String> unbindMuscleCategory(@RequestHeader(value = "Authorization") String token,
                                                       @PathVariable Long exerciseId,
                                                       @PathVariable String categoryName) throws Exception {
        if (validateAdmin(token)) {
            adminService.unbindMuscleCategory(exerciseId, categoryName);
            return new ResponseEntity<>("Muscle category successfully unbound from the exercise", HttpStatus.OK);
        } else {
            throw new Exception("Admin privileges required");
        }
    }


}
