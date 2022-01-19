package com.haier.rdtp.microservice.service.feign.decoder;

import org.springframework.stereotype.Component;
import com.haier.rdtp.microservice.service.constant.ErrorCodeEnum;
import com.haier.rdtp.microservice.service.exception.BizException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义feign解码器
 *
 *  - @Component注解，被spring管理，成为一个全局的feign解码器
 *  - 如果想要特殊处理的解码器，可以在 yml中指定：
*         - ${feign.client.config.xxxx.decoder}
*         - ${feign.client.config.xxxx.errorDecoder}
 *
 *  - feign接口正常返回时的解码器
 *    extends StringDecoder类， 重写 decode 方法
 *
 *  - feign接口异常时的解码器
 *    implements ErrorDecoder接口，实现 decode 方法
 *
 */
@Slf4j
@Component
public class DemoFeignErrorDecoder implements ErrorDecoder {

    /**
     * 异常解析器
     * 处理feign接口返回非200的情况
     * @param methodKey
     * @param response
     * @return
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("调用微服务接口：{},错误信息：{}", methodKey, response.reason());
        return new BizException(ErrorCodeEnum.FEIGN_API_REQUEST_FAILED, response.reason());
    }

}
