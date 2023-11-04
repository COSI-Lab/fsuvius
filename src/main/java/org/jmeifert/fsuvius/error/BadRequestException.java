package org.jmeifert.fsuvius.error;

/**
 * A BadRequestException is thrown when a request cannot be processed due to its nature or content.
 */
@SuppressWarnings("unused")
public class BadRequestException extends RuntimeException {
    /**
     * Throws a BadRequestException.
     */
    public BadRequestException() {
        super("Bad request.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() { return this; }
}
