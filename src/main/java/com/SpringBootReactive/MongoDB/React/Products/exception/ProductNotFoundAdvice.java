package com.SpringBootReactive.MongoDB.React.Products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ProductNotFoundExcpetion.class)//whenever this class is called this exception handler is called
    @ResponseStatus(HttpStatus.NOT_FOUND)//response for which the function runs
    public Map<String,String> exceptionHandler(ProductNotFoundExcpetion exception){

        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());

        return errorMap;

    }
}
