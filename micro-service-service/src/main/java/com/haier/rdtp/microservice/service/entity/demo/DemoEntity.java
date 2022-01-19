package com.haier.rdtp.microservice.service.entity.demo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 数据库实体类
 */
@Data
@TableName(value = "demo")
public class DemoEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orderNo;

}