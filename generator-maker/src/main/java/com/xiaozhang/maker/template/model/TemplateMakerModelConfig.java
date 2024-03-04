package com.xiaozhang.maker.template.model;

import com.xiaozhang.maker.meta.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author acal1314
 * 模型配置类
 */
@Data
public class TemplateMakerModelConfig {

    private ModelGroupConfig modelGroupConfig;
    private List<ModelInfo> models;
    @NoArgsConstructor
    @Data
    public static class ModelInfo {

        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String abbr;
        //要替换的文本
        private String replaceText;
    }


    @Data
    public static class ModelGroupConfig{
        private String groupKey;
        private String groupName;
        private String condition;
    }
}
