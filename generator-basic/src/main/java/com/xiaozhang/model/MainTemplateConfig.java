package com.xiaozhang.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author acal1314
 */
@Data
@Builder
public class MainTemplateConfig {

    private boolean loop;

    private String author="作者";
    private String output="Sum==";
}
