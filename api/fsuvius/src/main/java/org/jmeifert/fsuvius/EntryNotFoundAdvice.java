package org.jmeifert.fsuvius;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EntryNotFoundAdvice tells the controller what to do when an entry is not found.
 */
@ControllerAdvice
class EntryNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entryNotFoundHandler(EntryNotFoundException e) {
        return e.getMessage();
    }
}