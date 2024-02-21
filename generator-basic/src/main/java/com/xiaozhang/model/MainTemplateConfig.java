package com.xiaozhang.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author acal1314
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainTemplateConfig {

    private boolean loop;

    private String author="作者";
    private String output="Sum==";
}
