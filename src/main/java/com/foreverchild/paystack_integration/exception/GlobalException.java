package com.foreverchild.paystack_integration.exception;

import com.foreverchild.paystack_integration.dto.response.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(MethodArgumentNotValidException mae){
        Map<String,String> errorMap = new HashMap<>();

        mae.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put("status","false");
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return errorMap;

    }


}
