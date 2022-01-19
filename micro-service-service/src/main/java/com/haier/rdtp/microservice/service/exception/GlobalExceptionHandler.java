package com.haier.rdtp.microservice.service.exception;

import com.haier.gdp.pangu.common.dto.GlobalResponse;
import com.haier.rdtp.microservice.service.constant.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public GlobalResponse<String> defaultExceptionHandler(Exception ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();

        this.exceptionInfoHandle(ex);
        log.info("ex name:{}", ex.getClass().getName());

        // 参数校验异常 -- get请求的对象参数校验失败后抛出的异常
        if (ex instanceof BindException) {
            return this.failResponse(ErrorCodeEnum.ILLEGAL_PARAMETER, ((BindException) ex).getBindingResult());
        }

        // 参数校验异常 -- post请求的对象参数校验失败后抛出的异常
        if (ex instanceof MethodArgumentNotValidException) {
            return this.failResponse(ErrorCodeEnum.ILLEGAL_PARAMETER, ((MethodArgumentNotValidException)ex).getBindingResult());
        }

        // 参数校验异常 -- 单参数校验失败后抛出的异常
        if(ex instanceof ConstraintViolationException){
            String errorMsg = ((ConstraintViolationException)ex).getMessage();
            return GlobalResponse.fail(ErrorCodeEnum.ILLEGAL_PARAMETER.getCode(),
                    MessageFormat.format(ErrorCodeEnum.ILLEGAL_PARAMETER.getMessage(), errorMsg));
        }

        // 参数校验异常 -- 缺少必需的参数抛出的异常
        if(ex instanceof MissingServletRequestParameterException){
            String errorMsg = ((MissingServletRequestParameterException)ex).getMessage();
            return GlobalResponse.fail(ErrorCodeEnum.ILLEGAL_PARAMETER.getCode(),
                    MessageFormat.format(ErrorCodeEnum.ILLEGAL_PARAMETER.getMessage(), errorMsg));
        }

        // 自定义业务异常
        if (ex instanceof BizException) {
            BizException bz = (BizException) ex;
            return GlobalResponse.fail(bz.getCode(), bz.getMessage());
        }

        // 系统内部其它异常
        return this.failResponse(ErrorCodeEnum.INVALID_REQUEST, ex);
    }

    /**
     * 错误返回
     * @param errorCodeEnum
     * @param ex
     * @return
     */
    private GlobalResponse<String> failResponse(ErrorCodeEnum errorCodeEnum, Exception ex) {
        return GlobalResponse.fail(errorCodeEnum.getCode(), MessageFormat.format(errorCodeEnum.getMessage(), ex.toString()));
    }

    /**
     * 参数校验失败的返回处理
     * @param errorCodeEnum
     * @param bindingResult
     * @return
     */
    private GlobalResponse<String> failResponse(ErrorCodeEnum errorCodeEnum, BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder("[");
        bindingResult.getFieldErrors().forEach(fieldError ->
                errorMsg.append(fieldError.getField())
                        .append(":")
                        .append(fieldError.getDefaultMessage())
                        .append("; "));
        errorMsg.append("]");
        return GlobalResponse.fail(errorCodeEnum.getCode(), MessageFormat.format(errorCodeEnum.getMessage(), errorMsg));
    }

    /**
     * 异常信息处理方法
     * @param ex
     */
    private void exceptionInfoHandle(Exception ex) {
        ServletRequestAttributes servletContainer = null;
        try {
            servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            log.info("exceptionInfoHandle fail:{}", e.toString());
        }
        if (servletContainer == null) {
            return;
        }

        HttpServletRequest request = servletContainer.getRequest();
        String uri = "";
        uri = request.getRequestURI();
        if (request.getQueryString() != null) {
            uri = uri + "?" + request.getQueryString();
        }
        log.error("uri:{}, defaultExceptionHandler:{}", uri, ex.toString());

        // 异常记录
        Map<String, Object> params = new HashMap<>(6);
        params.put("method", request.getMethod());
        params.put("uri", uri);
        params.put("exception", ex.getClass().getName());
        params.put("message", ex.toString());
        params.put("body", this.getRequestBody(request));
        log.info("异常记录:{}", params);
    }

    /**
     * 获取请求体内容
     * @param request
     * @return
     */
    private String getRequestBody(HttpServletRequest request) {
        byte[] bodyBytes = new byte[0];
        String body = "";
        try {
            bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
            body = new String(bodyBytes, request.getCharacterEncoding());
        } catch (IOException e) {
            log.info("exceptionInfoHandle-getRequestBody exception:{}", e.toString());
        }
        return body;
    }
}
