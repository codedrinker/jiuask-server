package com.codedrinker.error;

/**
 * Created by codedrinker on 2018/11/25.
 */
public enum CommonErrorCode implements IErrorCode {
    NETWORK_ERROR(1001, "网络错误请重试"),
    INVALID_PARAMS(1002, "参数传递有误"),
    OBTAIN_OPENID_ERROR(1003, "登录失败，请重新尝试"),
    UNKOWN_ERROR(1004, "未知错误请重试"),
    NO_USER(1005, "登录异常，请重新登录"),
    SIGNATURE_ERROR(1006, "验证失败，传递信息有误"),
    ;

    private Integer code;
    private String message;


    CommonErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
