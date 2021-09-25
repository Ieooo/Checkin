package com.leo.checkin.exception;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/11 23:22
 */
public class AppException extends RuntimeException {
    public AppException() {

    }
    public AppException(String message) {
        super(message);
    }
}
