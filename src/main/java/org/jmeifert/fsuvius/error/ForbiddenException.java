package org.jmeifert.fsuvius.error;


/**
 * A ForbiddenException is thrown when a request attempts to access or modify
 * resources it is not allowed to do so with.
 */
public class ForbiddenException extends RuntimeException {
    /**
     * Throws a ForbiddenException.
     */
    public ForbiddenException() {
        super("Forbidden.");
    }
}
