package edu.clarkson.cosi.fsuvius.error;


/**
 * A NotFoundException is thrown when a request attempts to access a resource
 * that does not exist.
 */
@SuppressWarnings("unused")
public class NotFoundException extends RuntimeException {
    /**
     * Throws a NotFoundException.
     */
    public NotFoundException() {
        super("Not found.");
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() { return this; }
}
