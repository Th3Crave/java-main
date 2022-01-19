package com.haier.rdtp.microservice.service.service.demo;

import com.haier.rdtp.microservice.service.constant.ErrorCodeEnum;
import com.haier.rdtp.microservice.service.entity.demo.DemoEntity;
import com.haier.rdtp.microservice.api.dto.demo.DemoDTO;
import com.haier.rdtp.microservice.service.exception.BizException;
import com.haier.rdtp.microservice.service.mapper.demo.DemoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * service
 */
@Slf4j
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public DemoDTO demo(String materialCode) {
        return new DemoDTO();
    }

    public String testException() {
        // 此处模拟业务失败，抛出自定义异常
        throw new BizException(ErrorCodeEnum.TEST_EXCEPTION, "数据库连接失败");
    }
}






