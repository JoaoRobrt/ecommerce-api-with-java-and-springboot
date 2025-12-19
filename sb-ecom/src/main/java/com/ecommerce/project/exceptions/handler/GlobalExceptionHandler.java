package com.ecommerce.project.exceptions.handler;

import com.ecommerce.project.exceptions.ExceptionResponse;
import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoudException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoudException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(ResourceNotFoudException e,
                                                                           WebRequest request){
        ExceptionResponse body = new ExceptionResponse(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                e.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                null);

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException e,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ExceptionResponse body = new ExceptionResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Invalid request data. Please check the fields.",
                request.getDescription(false).replace("uri=", ""),
                errors
            );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleAlreadyExists(
            ResourceAlreadyExistsException e,
            WebRequest request){

        ExceptionResponse body = new ExceptionResponse(
                new Date(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                e.getMessage(),
                request.getDescription(false).replace("uri = ", ""),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    //ERRO INTERNO NÃO ESPERADO
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleUnexpectedExceptions(Exception e, WebRequest request) {
        ExceptionResponse body = new ExceptionResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Internal server error. Please try again later.",
                request.getDescription(false).replace("uri=", ""),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
