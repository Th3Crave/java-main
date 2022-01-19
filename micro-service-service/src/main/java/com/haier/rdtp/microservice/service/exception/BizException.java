package com.haier.rdtp.microservice.service.exception;

import com.haier.rdtp.microservice.service.constant.ErrorCodeEnum;
import lombok.Data;

import java.text.MessageFormat;

/**
 * 自定义业务异常
 */
@Data
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;

    public BizException(ErrorCodeEnum errorCodeEnum, String reason){
        super(MessageFormat.format(errorCodeEnum.getMessage(), reason));
        this.code = errorCodeEnum.getCode();
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }
}
