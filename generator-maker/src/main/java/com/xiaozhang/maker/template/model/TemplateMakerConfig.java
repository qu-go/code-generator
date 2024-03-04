package com.xiaozhang.maker.template.model;

import com.xiaozhang.maker.meta.Meta;
import lombok.Data;

/**
 * 制作模板需要参数的封装类
 *
 * @author acal1314
 */
@Data
public class TemplateMakerConfig {

    private Long id;

    private Meta mete = new Meta();
    private String originProjectPath;
    private TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();
    private TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    private TemplateOutputConfig templateOutputConfig=new TemplateOutputConfig();

}
