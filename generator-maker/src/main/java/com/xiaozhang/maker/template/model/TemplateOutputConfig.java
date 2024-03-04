package com.xiaozhang.maker.template.model;

import lombok.Data;

/**
 * @author acal1314
 * 模板文件输出路径配置
 */
@Data
public class TemplateOutputConfig {

    /**
     * 从未分组文件暗中移除组内同名文件
     */
    private boolean removeGroupFileFromRoot=true;

}
