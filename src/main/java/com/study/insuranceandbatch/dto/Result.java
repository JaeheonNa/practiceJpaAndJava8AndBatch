package com.study.insuranceandbatch.dto;

import com.study.insuranceandbatch.common.CommonConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String message;
    private Object data;

    private Result setResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public Result success() {
        return setResult(CommonConstant.RESULT_SUCCESS, CommonConstant.RESULT_SUCCESS_MESSAGE, null);
    }

    public Result success(Object data) {
        return setResult(CommonConstant.RESULT_SUCCESS, CommonConstant.RESULT_SUCCESS_MESSAGE, data);
    }

    public Result fail() {
        return setResult(CommonConstant.RESULT_FAIL, CommonConstant.RESULT_FAIL_MESSAGE, null);
    }

    public Result fail(String message, int code) {
        return setResult(code, message, null);
    }

    public Result fail(Object data, String message) {
        return setResult(CommonConstant.RESULT_FAIL, message, data);
    }

    public Result fail(Object data, String message, int code) {
        return setResult(code, message, data);
    }

    public Result fail(Object data) {
        return setResult(CommonConstant.RESULT_FAIL, CommonConstant.RESULT_FAIL_MESSAGE, data);
    }
}
