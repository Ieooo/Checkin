package com.leo.checkin.exception;

import com.leo.checkin.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/11 23:23
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result constraintViolationExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException:{}", e);
        return Result.fail(this.getErrorMessages(e));
    }


    @ExceptionHandler(AppException.class)
    public Result<String> appExceptionHandler(AppException e) {
        log.error("AppException:{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error("Exception:{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    private String getErrorMessages(Exception e) {
        StringBuilder msg = new StringBuilder();
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException)e).getBindingResult().getFieldErrors();
        }

        if (e instanceof BindException) {
            fieldErrors = ((BindException)e).getBindingResult().getFieldErrors();
        }

        Iterator var4 = fieldErrors.iterator();

        while(var4.hasNext()) {
            FieldError error = (FieldError)var4.next();
            msg.append(String.join(",", error.getDefaultMessage()));
        }

        return msg.toString();
    }
}
