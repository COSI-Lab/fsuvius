package org.jmeifert.fsuvius.security;

public class RateLimitException extends RuntimeException {
    public RateLimitException() {
        super("Connection throttled.");
    }
}
