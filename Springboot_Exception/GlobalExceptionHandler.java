package com.fourbench.schoolmanagementservice.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public ResponseEntity<ErrorResponse> handleHttpStatusCodeException(HttpStatusCodeException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(ex.getStatusText(),
                HttpStatus.valueOf(ex.getStatusCode().value()).name(),
                ex.getResponseBodyAsString(), request.getRequestURI());
        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            errors.add(globalError.getObjectName() + ": " + globalError.getDefaultMessage());
        }
        ErrorResponse body = getBody(request, errors, "METHOD_ARGUMENT_NOT_VALID", HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ErrorResponse body = getBody(request, errors, "CONSTRAINT_VIOLATED", HttpStatus.BAD_REQUEST.name());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(org.springframework.validation.BindException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            errors.add(globalError.getObjectName() + ": " + globalError.getDefaultMessage());
        }
        ErrorResponse body = getBody(request, errors, "ARGUMENT_TYPE_OR_REQUIRED_FIELD_MISMATCH", HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex, HttpServletRequest request) {
        String error = ex.getPath().toString() + ": Invalid format. Expected " + ex.getTargetType().getSimpleName();

        ErrorResponse body = getBody(request, List.of(error), "INVALID_FORMAT", HttpStatus.BAD_REQUEST.name());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException ex, HttpServletRequest request) {
        String error = ex.getParsedString() + ": Invalid date format.";

        ErrorResponse body = getBody(request, List.of(error), "INVALID_DATE_FORMAT", HttpStatus.BAD_REQUEST.name());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex, HttpServletRequest request) {
        String error = Objects.requireNonNull(ex.getRootCause()).getMessage();
        ErrorResponse body = getBody(request, List.of(error), "MESSAGE_NOT_READABLE", HttpStatus.BAD_REQUEST.name());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String error = ex.getName().concat(" should be of type ").concat(ex.getRequiredType().getSimpleName());
        ErrorResponse body = getBody(request, List.of(error), "INVALID_METHOD_ARGUMENT_TYPE", HttpStatus.BAD_REQUEST.name());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private static ErrorResponse getBody(HttpServletRequest request, List<String> errors, String status, String httpStatusCode) {
        return new ErrorResponse(
                status,
                httpStatusCode,
                errors,
                request.getRequestURI());
    }
}