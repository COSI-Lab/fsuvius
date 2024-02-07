package edu.clarkson.cosi.fsuvius.error;


/**
 * A RateLimitException is thrown when a client request is throttled.
 */
@SuppressWarnings("unused")
public class RateLimitException extends RuntimeException {
    /**
     * Throws a RateLimitException
     */
    public RateLimitException() {
        super("Connection throttled.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() { return this; }

}
