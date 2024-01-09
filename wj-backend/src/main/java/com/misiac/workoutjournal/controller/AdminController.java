package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import com.misiac.workoutjournal.service.AdminService;
import com.misiac.workoutjournal.util.AdminValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.misiac.workoutjournal.util.MessageProvider.*;

@Validated
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminValidator adminValidator;

    @Autowired
    public AdminController(AdminService adminService, AdminValidator adminValidator) {
        this.adminService = adminService;
        this.adminValidator = adminValidator;
    }

    @PostMapping("/muscle-category")
    public ResponseEntity<String> addMuscleGroupCategory(@RequestHeader(value = "Authorization") String token,
                                                         @RequestParam @NotBlank(message=REQUEST_NAME_BLANK) String name)

    {
        if (adminValidator.validateAdmin(token)) {
            adminService.addMuscleGroupCategory(name);
            return new ResponseEntity<>(CATEGORY_CREATED, HttpStatus.CREATED);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }

    @PostMapping("/equipment-category")
    public ResponseEntity<String> addEquipmentCategory(@RequestHeader(value = "Authorization") String token,
                                                       @RequestParam @NotBlank(message = REQUEST_NAME_BLANK) String name) {
        if (adminValidator.validateAdmin(token)) {
            adminService.addEquipmentCategory(name);
            return new ResponseEntity<>(CATEGORY_CREATED, HttpStatus.CREATED);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }

    @PostMapping("/exercise")
    public ResponseEntity<String> addExercise(@RequestHeader(value = "Authorization") String token,
                                              @Valid @RequestBody AdminCreateExerciseRequest adminExerciseRequest) {
        if (adminValidator.validateAdmin(token)) {
            adminService.addExercise(adminExerciseRequest);
            return new ResponseEntity<>(EXERCISE_CREATED, HttpStatus.CREATED);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }

    @PatchMapping("/exercise/{exerciseId}/equipment-categories/{categoryName}")
    public ResponseEntity<String> bindEquipmentCategory
            (@RequestHeader(value = "Authorization") String token, @PathVariable Long exerciseId,
             @PathVariable  String categoryName) {
        if (adminValidator.validateAdmin(token)) {
            adminService.bindEquipmentCategory(exerciseId, categoryName);
            return new ResponseEntity<>(CATEGORY_BOUND, HttpStatus.OK);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }

    @DeleteMapping("/exercise/{exerciseId}/equipment-categories/{categoryName}")
    public ResponseEntity<String> unbindEquipmentCategory(@RequestHeader(value = "Authorization") String token,
                                                          @PathVariable Long exerciseId, @PathVariable String categoryName) {
        if (adminValidator.validateAdmin(token)) {
            adminService.unbindEquipmentCategory(exerciseId, categoryName);
            return new ResponseEntity<>(CATEGORY_UNBOUND, HttpStatus.OK);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }

    @PatchMapping("/exercise/{exerciseId}/muscle-categories/{categoryName}")
    public ResponseEntity<String> bindMuscleCategory(@RequestHeader(value = "Authorization") String token,
                                                     @PathVariable Long exerciseId, @PathVariable String categoryName,
                                                     @RequestParam boolean isPrimary) {
        if (adminValidator.validateAdmin(token)) {
            adminService.bindMuscleCategory(exerciseId, categoryName, isPrimary);
            return new ResponseEntity<>(CATEGORY_BOUND, HttpStatus.OK);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }

    @DeleteMapping("/exercise/{exerciseId}/muscle-categories/{categoryName}")
    public ResponseEntity<String> unbindMuscleCategory(@RequestHeader(value = "Authorization") String token,
                                                       @PathVariable Long exerciseId,
                                                       @PathVariable String categoryName) {
        if (adminValidator.validateAdmin(token)) {
            adminService.unbindMuscleCategory(exerciseId, categoryName);
            return new ResponseEntity<>(CATEGORY_UNBOUND, HttpStatus.OK);
        } else {
            throw new UnauthorizedException(ADMIN_REQUIRED);
        }
    }


}
