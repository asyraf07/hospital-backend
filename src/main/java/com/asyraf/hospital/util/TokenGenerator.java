package com.asyraf.hospital.util;

import java.util.UUID;

public class TokenGenerator {

    private TokenGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String generate() {
        return UUID.randomUUID().toString();
    }
    
}
