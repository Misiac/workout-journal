package com.misiac.workoutjournal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminValidator {
    private final JWTExtractor jwtExtractor;

    @Autowired
    public AdminValidator(JWTExtractor jwtExtractor) {
        this.jwtExtractor = jwtExtractor;
    }

    public boolean validateAdmin(String token) {
        String role = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.ROLE);
        return role != null && role.equals("admin");
    }
}
