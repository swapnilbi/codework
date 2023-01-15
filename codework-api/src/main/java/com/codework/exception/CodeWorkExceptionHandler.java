package com.codework.exception;

import com.codework.enums.RemarkType;
import com.codework.model.Remark;
import com.codework.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CodeWorkExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        log.error("handleValidationExceptions ",exception);
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException businessException) {
        log.error("handleBusinessException ",businessException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage(businessException.getMessage());
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity handleBadCredentialsException(SecurityException securityException) {
        log.error("handleBadCredentialsException ",securityException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage(securityException.getMessage());
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity handleAccessDeniedException(LockedException lockedException) {
        log.error("handleAccessDeniedException ",lockedException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage(lockedException.getMessage());
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        log.error("AccessDeniedException ",accessDeniedException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage(accessDeniedException.getMessage());
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericException(Exception businessException) {
        log.error("handleGenericException ",businessException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage("Something went wrong. Please try again");
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity handleSystemException(SystemException systemException) {
        log.error("handleSystemException ",systemException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage(systemException.getMessage());
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity(errors,HttpStatus.BAD_REQUEST);
    }
     */
}
