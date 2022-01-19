package com.haier.rdtp.microservice.api.dto.demo;

import com.haier.gdp.pangu.common.pagination.PageRequest;
import lombok.Data;


/**
 * 入参 -查询
 *
 */
@Data
public class DemoQueryDTO extends PageRequest {
    private static final long serialVersionUID = 1L;

    private String versionCode;

}
