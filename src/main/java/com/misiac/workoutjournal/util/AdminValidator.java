package com.misiac.workoutjournal.util;

public class AdminValidator {

    public static boolean validateAdmin(String token) {
        String role = JWTExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.ROLE);
        return role != null && role.equals("admin");
    }
}
