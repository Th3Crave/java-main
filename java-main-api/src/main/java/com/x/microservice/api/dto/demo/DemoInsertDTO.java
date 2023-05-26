package com.x.microservice.api.dto.demo;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 入参 -插入
 *
 */
@Data
public class DemoInsertDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String versionCode;

}
