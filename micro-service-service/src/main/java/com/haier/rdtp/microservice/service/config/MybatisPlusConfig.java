package com.haier.rdtp.microservice.service.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 分页插件配置信息
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为MySQL
        innerInterceptor.setDbType(DbType.MYSQL);

        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }

}
