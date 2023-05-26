package com.x.microservice.api.dto.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * 出参
 *
 */
@Data
public class DemoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String versionCode;

}
