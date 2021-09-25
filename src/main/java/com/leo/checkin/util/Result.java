package com.leo.checkin.util;

import lombok.Data;

/**
 * @Description:
 * @Author: leo
 * @Date: 2021/9/11 23:03
 */
@Data
public class Result<T> {
    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回状态(true or false)
     */
    private Boolean status;

    public static Result success() {
        Result result = new Result();
        result.setStatus(true);
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setStatus(false);
        return result;
    }

    public static <T> Result<T> fail(T data) {
        Result<T> result = new Result();
        result.setStatus(false);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T date) {
        Result<T> result = new Result<T>();
        result.setData(date);
        result.setStatus(true);
        return result;
    }
}
