package com.x.microservice.api.dto.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * 入参 -查询
 *
 */
@Data
public class DemoQuery2DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String versionCode;
}