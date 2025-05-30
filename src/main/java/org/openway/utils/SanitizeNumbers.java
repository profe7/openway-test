package org.openway.utils;


public class SanitizeNumbers {
    public static long sanitizeNumbers(String price) {
        if (price == null || price.isEmpty()) {
            return 0L;
        }
        String numeric = price.replaceAll("[^\\d]", "");
        if (numeric.isEmpty()) {
            return 0L;
        }
        return Long.parseLong(numeric);
    }
}
