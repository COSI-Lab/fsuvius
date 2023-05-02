package org.jmeifert.fsuvius.error;


/**
 * A RateLimitException is thrown when a client request is throttled.
 */
public class RateLimitException extends RuntimeException {
    /**
     * Throws a RateLimitException
     */
    public RateLimitException() {
        super("Connection throttled.");
    }
}
