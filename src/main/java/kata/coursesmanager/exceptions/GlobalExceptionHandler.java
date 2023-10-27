package kata.coursesmanager.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>("Une erreur interne s'est produite.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<String> handleFunctionalException(FunctionalException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}