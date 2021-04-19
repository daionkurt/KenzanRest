package com.kenzan.rest.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@ControllerAdvice
@RestController
class MyExceptionHandler  extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(MyExceptionHandler.class);

    @ExceptionHandler({ Exception.class })
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        String message = "";
        if (ex.getMessage() == null || ex.getMessage().isEmpty()) {
            if (ex instanceof NullPointerException) {
                message = "Null pointer detected during the process of this request. Try again or contact the system administrator";
            } else {
                message = "Something went wrong. Try again or contact the system administrator";
            }
        } else {
            message = ex.getMessage();
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message, request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ EmployeeNotFoundException.class, NoEmployeesFoundException.class })
    public final ResponseEntity<Object> handleEmployeeNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ DuplicateEmployeeException.class})
    public final ResponseEntity<Object> handleDuplicateCountryException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public final ResponseEntity<Object> handleInvalidArgumentFormatException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NoGrantException.class })
    public final ResponseEntity<Object> handleNoGrantException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        logError(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }


    private void logError(Exception ex) {
        LOGGER.error(ex.getMessage());
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        LOGGER.error(writer.toString());
    }

}
