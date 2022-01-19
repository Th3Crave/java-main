package com.haier.rdtp.microservice.service.constant;

import lombok.Getter;

/**
 * 错误码枚举类，所有错误码都要在这里定义
 */
@Getter
public enum ErrorCodeEnum {
    // demo错误码定义 start
    /**
     * 无效请求
     */
    INVALID_REQUEST("InvalidRequest", "Invalid request, for reason: {0}"),

    /**
     * 参数校验未通过
     */
    ILLEGAL_PARAMETER("Illegal parameter", "参数校验未通过, for reason: {0}"),

    /**
     * 测试业务处理过程中遇到的异常的错误码
     */
    TEST_EXCEPTION("TestException", "系统处理异常，for reason: {0}"),

    /**
     * feign第三方接口出错
     */
    FEIGN_API_REQUEST_FAILED("Feign api request failed", "for reason: {0}"),
    GATEWAY_FEIGN_API_REQUEST_FAILED("Gateway feign api request failed", "for reason: {0}")
    // demo错误码定义 end




    ;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

}
