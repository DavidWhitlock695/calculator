package com.example.calculator.handler;

import com.example.calculator.exception.BusinessRuleViolationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

/**
 * The annotation @RestControllerAdvice intercepts exceptions thrown by any controller with
 * RestController annotation.
 * Note there are other ways to handle exceptions both globally and locally in Spring.
 * To implement this correctly we will be using the RFC 7807 standard for error responses.
 * Spring Boot implements this standard using the ProblemDetail class.
**/

@RestControllerAdvice
// TODO: add logging dependency annotation.
public class GlobalExceptionHandler {

  // We pass in the request - spring boot's record of the incoming request - to provide more context about the error.
  // This object is available to us all the way down in the call stack, so we can use it in any exception handler method.

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ProblemDetail> handleEntityNotFound(EntityNotFoundException ex,
                                                            HttpServletRequest request){

    // Start by creating a problem detail object with the appropriate status and message.
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
    );

    // Set additional properties to provide more context about the error.
    // Custom URI that can be navigated to for more information about the error type.
    // Since we don't own a domain with real links, we provide a URN (Uniform Resource Name)
    // This represents a unique name, not a location, and is a common convention in APIs.
    // TODO: we could build GET endpoints that cater to these with a dynamic base path...
    problem.setType(URI.create("urn:problem-type:entity-not-found"));
    // A human-readable title for the error.
    problem.setTitle("Entity Not Found");
    // Describes what endpoint the user was trying to access.
    problem.setInstance(URI.create(request.getRequestURI()));
    // Timestamp including helpful timezone info.
    problem.setProperty("timestamp", Instant.now());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<ProblemDetail> handleEntityExists(EntityExistsException ex,
                                                          HttpServletRequest request){
  ProblemDetail problem = ProblemDetail.forStatusAndDetail(
          HttpStatus.CONFLICT,
          ex.getMessage()
  );
  problem.setType(URI.create("urn:problem-type:entity-already-exists"));
  problem.setTitle("Entity Already Exists");
  problem.setInstance(URI.create(request.getRequestURI()));
  problem.setProperty("timestamp", Instant.now());

  return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
  }

  @ExceptionHandler(BusinessRuleViolationException.class)
  public ResponseEntity<ProblemDetail> handleBusinessRuleViolation(BusinessRuleViolationException ex,
                                                                   HttpServletRequest request){
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
    );
    problem.setType(URI.create("urn:problem-type:business-rule-violation"));
    problem.setTitle("Business Rule Violation");
    problem.setInstance(URI.create(request.getRequestURI()));
    problem.setProperty("ruleType", ex.getBusinessRuleType().name());
    if (!ex.getViolationValues().isEmpty()) {
      problem.setProperty("violationDetails", ex.getViolationValues());
    }
    problem.setProperty("timestamp", Instant.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem); }
}
