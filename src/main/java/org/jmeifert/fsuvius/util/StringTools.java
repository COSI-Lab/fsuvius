package org.jmeifert.fsuvius.util;

public class StringTools {
    public static String sanitizeLine(String s) {
        return s.replaceAll("[^a-zA-Z0-9 '!@#$%^&*()/.,;:_=-]", "");
    }

    public static String sanitizeField(String s) {
        return s.replaceAll("[^a-zA-Z0-9 \n'!@#$%^&*()/.,;:_=-]", "");
    }
}
