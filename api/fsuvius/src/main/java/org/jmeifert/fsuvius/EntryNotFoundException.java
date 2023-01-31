package org.jmeifert.fsuvius;

/**
 * An EntryNotFoundException is thrown when an entry cannot be found.
 */
public class EntryNotFoundException extends RuntimeException {
    /**
     * Constructs an EntryNotFoundException with a given ID.
     * @param id ID of the nonexistent entry
     */
    public EntryNotFoundException(Long id) {
        super("Entry " + id + " not found.");
    }
}
