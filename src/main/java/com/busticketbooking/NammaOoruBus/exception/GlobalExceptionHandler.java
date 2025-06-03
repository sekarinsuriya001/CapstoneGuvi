package com.busticketbooking.NammaOoruBus.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        return getErrorDetailsResponseEntity(exception.getMessage(), "USER_NOT_FOUND", webRequest, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailPresent.class)
    public ResponseEntity<ErrorDetails> handlesEmailPresent(EmailPresent exception, WebRequest webRequest){
        return getErrorDetailsResponseEntity(exception.getMessage(), "EMAIL_FOUND", webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateBusNumberException.class)
    public ResponseEntity<ErrorDetails> handlesBusNotFound(DuplicateBusNumberException exception,WebRequest webRequest){
        return getErrorDetailsResponseEntity(exception.getMessage(), "BUS_NUMBER_FOUND", webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberPresent.class)
    public ResponseEntity<ErrorDetails> handlesPhoneNumberPresent(PhoneNumberPresent exception,WebRequest webRequest){
        return getErrorDetailsResponseEntity(exception.getMessage(), "PHONENUMBER_FOUND", webRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
        return getErrorDetailsResponseEntity(exception.getMessage(), "USER_NOT_FOUND", webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<ErrorDetails> getErrorDetailsResponseEntity(String exception, String errorCode, WebRequest webRequest, HttpStatus notFound) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setLocalDateTime(LocalDateTime.now());
        errorDetails.setMessage(exception);
        errorDetails.setErrorCode(errorCode);
        errorDetails.setPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, notFound);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        Map<String,String> error = new HashMap<>();
        List<ObjectError> objectErrors= ex.getBindingResult().getAllErrors();
        objectErrors.forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;
            error.put(fieldError.getField(),objectError.getDefaultMessage());
        });

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}