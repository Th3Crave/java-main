package com.haier.rdtp.microservice.api.feignclient;

import com.haier.gdp.pangu.common.dto.GlobalResponse;
import com.haier.gdp.pangu.common.pagination.PageRequest;
import com.haier.gdp.pangu.common.pagination.PageResponse;
import com.haier.rdtp.microservice.api.dto.demo.DemoDTO;
import com.haier.rdtp.microservice.api.dto.demo.DemoInsertDTO;
import com.haier.rdtp.microservice.api.dto.demo.DemoQueryDTO;
import com.haier.rdtp.microservice.api.dto.demo.DemoQuery2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 说明:
 *
 * 1. FeignClient 提供给其它服务直接使用的feignClient
 *     url   -提供方的服务地址, 调用方在配置文件中配置此项;
 *     name  -提供方的feign name, 需遵循命名规范, 以防重复;
 *
 * 2. 在feignClient中定义controller需要实现的方法, @RequestMapping也都在这里配置
 *
 */
@FeignClient(name = "xxxproject-xxxservice-api", url = "${feign.client.config.xxxproject-xxxservice-api.url}")
@RequestMapping("/demo")
public interface DemoClient {
    /**
     * 查询所有商品信息，不进行分页
     * @param query
     * @return
     */
    @GetMapping("/list")
    GlobalResponse<List<DemoDTO>> list(@Validated DemoQueryDTO query);

    /**
     * 分页查询，ProductQueryDTO 类继承 PageRequest 分页请求基类
     * 参数校验不通过时，会抛 BindException 异常，会在全局异常处理类 GlobalExceptionHandler 中统一处理
     * @param query
     * @return
     */
    @GetMapping("/select-page")
    GlobalResponse<PageResponse<DemoDTO>> selectPage(@Validated DemoQueryDTO query);

    /**
     * 分页查询示例2，如果 DTO 已经继承了其它类，无法继承 PageRequest 分页请求基类，则可以通过如下方式
     * @param query
     * @return
     */
    @GetMapping("/select-page2")
    GlobalResponse<PageResponse<DemoDTO>> selectPage2(@Validated DemoQuery2DTO query, PageRequest pageRequest);

    /**
     * 新增商品
     * @param param
     * @return
     */
    @PostMapping
    GlobalResponse<Boolean> add(@Validated @RequestBody DemoInsertDTO param);

    /**
     * 此方法用于测试普通的网关调用方式，及没有引入下游系统的api Jar包
     * @return
     */
    @GetMapping("test-gateway")
    GlobalResponse<?> test();

    /**
     * 此方法用于测试业务异常场景
     * @return
     */
    @GetMapping("test-exception")
    GlobalResponse<String> testException();
}
