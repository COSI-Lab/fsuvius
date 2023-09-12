package org.jmeifert.fsuvius.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ForbiddenAdvice provides responses to forbidden requests.
 */
@ControllerAdvice
public class ForbiddenAdvice {
    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String forbiddenHandler(ForbiddenException e) {
        return e.getMessage();
    }
}
