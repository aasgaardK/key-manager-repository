package cz.spsbrno.keymanager.exception.handler;

import cz.spsbrno.keymanager.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//jestli nebudete používat endpointy, tak se toto nebude fungovat, asi

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    /**
     * Handle InvalidOperationException and return UNPROCESSABLE_ENTITY response with message specified in exception
     */
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<String> handleInvalidOperationException(final InvalidOperationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

}
