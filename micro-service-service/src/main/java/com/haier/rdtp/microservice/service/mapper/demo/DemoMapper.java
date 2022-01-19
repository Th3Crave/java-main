package com.haier.rdtp.microservice.service.mapper.demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.haier.rdtp.microservice.service.entity.demo.DemoEntity;

/**
 * mapper
 */
@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity> {

}