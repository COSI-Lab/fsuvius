package org.jmeifert.fsuvius.error;

public class RateLimitException extends RuntimeException {
    public RateLimitException() {
        super("Connection throttled.");
    }
}
