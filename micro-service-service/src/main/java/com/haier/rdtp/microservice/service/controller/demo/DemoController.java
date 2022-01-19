package com.haier.rdtp.microservice.service.controller.demo;

import com.haier.gdp.pangu.common.dto.GlobalResponse;
import com.haier.gdp.pangu.common.pagination.PageRequest;
import com.haier.gdp.pangu.common.pagination.PageResponse;
import com.haier.rdtp.microservice.api.dto.demo.DemoDTO;
import com.haier.rdtp.microservice.api.dto.demo.DemoInsertDTO;
import com.haier.rdtp.microservice.api.dto.demo.DemoQueryDTO;
import com.haier.rdtp.microservice.api.dto.demo.DemoQuery2DTO;
import com.haier.rdtp.microservice.api.feignclient.DemoClient;
import com.haier.rdtp.microservice.service.service.demo.DemoService;
import com.haier.rdtp.microservice.service.feign.client.GatewayDemoClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 * 实现 micro-service-api 模块中的 feignClient 接口
 * 每个 controller 都会在 micro-service-api 中对应一个 feignClient 接口
 */
@Slf4j
@RestController
@Api(tags = "demo controller")
public class DemoController implements DemoClient {

    @Autowired
    private GatewayDemoClient gatewayDemoClient;
    @Autowired
    private DemoService demoService;

    @Override
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionCode", value = "描述", dataType = "String")
    })
    public GlobalResponse<List<DemoDTO>> list(DemoQueryDTO query) {
        return GlobalResponse.success(null);
    }

    @Override
    @ApiOperation(value = "查询列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionCode", value = "描述", dataType = "String")
    })
    public GlobalResponse<PageResponse<DemoDTO>> selectPage(DemoQueryDTO query) {
        return GlobalResponse.success(null);
    }

    @Override
    @ApiOperation(value = "查询列表-分页示例2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionCode", value = "描述", dataType = "String")
    })
    public GlobalResponse<PageResponse<DemoDTO>> selectPage2(DemoQuery2DTO query, PageRequest pageRequest) {
        return GlobalResponse.success(null);
    }

    @Override
    @ApiOperation(value = "新增")
    public GlobalResponse<Boolean> add(DemoInsertDTO param) {
        return GlobalResponse.success(null);
    }

    @Override
    @ApiOperation(value = "测试网关调用")
    public GlobalResponse<?> test() {
        return GlobalResponse.success(gatewayDemoClient.get("CQ3869-001").getData());
    }

    @Override
    @ApiOperation(value = "测试业务场景异常的处理")
    public GlobalResponse<String> testException() {
        return GlobalResponse.success(demoService.testException());
    }
}
