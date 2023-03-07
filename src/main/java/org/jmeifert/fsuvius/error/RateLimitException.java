package org.jmeifert.fsuvius.error;


/**
 * A RateLimitException is raised when a client request is throttled.
 */
public class RateLimitException extends RuntimeException {
    public RateLimitException() {
        super("Connection throttled.");
    }
}
