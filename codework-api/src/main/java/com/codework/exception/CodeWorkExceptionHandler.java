package com.codework.exception;

import com.codework.enums.RemarkType;
import com.codework.model.Remark;
import com.codework.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CodeWorkExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(CodeWorkExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
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
        logger.warn(businessException.getMessage());
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage(businessException.getMessage());
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericException(Exception businessException) {
        logger.error("Exception ",businessException);
        Response response = new Response();
        Remark remark = new Remark();
        remark.setMessage("Something went wrong. Please try again");
        remark.setType(RemarkType.ERROR);
        response.setRemarks(Arrays.asList(remark));
        return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity handleSystemException(SystemException systemException) {
        logger.error(systemException.getMessage());
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
