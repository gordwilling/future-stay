package com.gordwilling.futurestay.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    final Logger log = LoggerFactory.getLogger(getClass());

    private final Lion lion;

    @Inject
    public GlobalExceptionHandler(Lion lion) {
        this.lion = lion;
    }

    /**
     * Invoked when validation fails on an object. In the case of validation being applied to an incoming request,
     * this would happen after deserialization
     * @param e the exception that was thrown
     * @param locale the client locale
     * @return a map containing the list of validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException e, Locale locale) {
        log.info(e.getMessage());
        var errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> format("%s: %s", error.getField(), error.getDefaultMessage()))
                .toList();

        String nameOfErrorsList = lion.of("global.exception.handler.bind.exception.errors", locale);

        var errorsMap = Map.of(nameOfErrorsList, errors);
        return new ResponseEntity<>(errorsMap, BAD_REQUEST);
    }

    /**
     * Invoked when the request body cannot be deserialized into its corresponding Java object.
     * @param e the exception that was thrown
     * @param locale the client locale
     * @return a generic error message, as there isn't much detail provided by the framework
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handle(HttpMessageNotReadableException e, Locale locale) {
        log.info(e.getMessage());
        String message = lion.of("validation.error.invalid.request.body", locale);
        return new ResponseEntity<>(new ApiError(message), BAD_REQUEST);
    }
}
