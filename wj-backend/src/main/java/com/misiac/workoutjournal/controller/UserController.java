package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.UserService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JWTExtractor jwtExtractor;

    @Autowired
    public UserController(UserService userService, JWTExtractor jwtExtractor) {
        this.userService = userService;
        this.jwtExtractor = jwtExtractor;
    }

    @PostMapping("/check")
    public void checkIfUserExistsInDb(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        userService.checkIfUserExistsInDb(email);
    }
}
