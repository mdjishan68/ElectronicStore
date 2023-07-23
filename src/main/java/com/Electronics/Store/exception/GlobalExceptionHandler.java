package com.Electronics.Store.exception;

import com.Electronics.Store.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Handler Resource Not Found Exception

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        logger.info("Exception handler invoked !!");
        ApiResponseMessage response =ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity(response,HttpStatus.NOT_FOUND);
    }

    //MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors =ex.getBindingResult().getAllErrors();
        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach(ObjectError -> {
            String message = ObjectError.getDefaultMessage();
            String field =((FieldError)ObjectError).getField();
            response.put(field,message);
        });
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    // handling bad request exception
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex)
    {
        logger.info("Bad Api Request !!");
        ApiResponseMessage response =ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }
}
