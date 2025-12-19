package com.ecommerce.project.exceptions.handler;

import com.ecommerce.project.exceptions.ExceptionResponse;
import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoudException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
    public final ProblemDetail handleNotFound(ResourceNotFoudException e){

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Resource not found");
        problem.setDetail(e.getMessage());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException e) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validation failed");
        problem.setDetail("One or more fields are invalid.");

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        problem.setProperty("errors", errors);

        return problem;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ProblemDetail handleConflict(ResourceAlreadyExistsException e){

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Resource conflict");
        problem.setDetail(e.getMessage());

        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request parameter");
        problem.setDetail(ex.getMessage());
        problem.setProperty("path", request.getRequestURI());

        return ResponseEntity.badRequest().body(problem);
    }

    //ERRO INTERNO NÃO ESPERADO
    @ExceptionHandler(Exception.class)
    public final ProblemDetail handleUnexpected(Exception e) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Internal server error");
        problem.setDetail("An unexpected error occurred, please try again later");

        return problem;
    }

}
