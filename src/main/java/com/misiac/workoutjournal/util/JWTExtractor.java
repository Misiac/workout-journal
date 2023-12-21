package com.misiac.workoutjournal.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JWTExtractor {
    public static String extractTokenParameter(String token, ExtractionType extraction) {

        String raw = token.replace("Bearer ", "");
        String[] chunks = raw.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        String[] entries = payload.split(",");
        Map<String, String> map = new HashMap<>();

        for (String entry : entries) {
            String[] keyValue = entry.split(":");

            if (keyValue[0].equals("\"" + extraction.value + "\"")) {
                int remove = 1;

                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }
        }
        if (map.containsKey("\"" + extraction.value + "\"")) {
            return map.get("\"" + extraction.value + "\"");
        }
        return null;
    }

    public enum ExtractionType {
        EMAIL("sub"),
        ROLE("userType");
        private final String value;

        ExtractionType(String value) {
            this.value = value;
        }

    }
}
