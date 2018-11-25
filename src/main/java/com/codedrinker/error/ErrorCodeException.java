package com.codedrinker.error;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by codedrinker on 2018/11/25.
 */
public class ErrorCodeException extends RuntimeException implements IErrorCode {
    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String message;

    public ErrorCodeException(IErrorCode iErrorCode) {
        this.code = iErrorCode.getCode();
        this.message = iErrorCode.getMessage();
    }

    @Override
    public String toString() {
        return "ErrorCodeException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
