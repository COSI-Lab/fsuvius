package org.jmeifert.fsuvius.error;


/**
 * A NotFoundException is raised when a request attempts to access a resource
 * that does not exist.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Not found.");
    }
}
