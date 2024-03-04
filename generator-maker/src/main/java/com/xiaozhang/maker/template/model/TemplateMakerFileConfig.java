package com.xiaozhang.maker.template.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author acal1314
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;
    private FileGroupConfig fileGroupConfig;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileInfoConfig{
        private String path;
        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    public static class FileGroupConfig{
        private String condition;
        private String groupKey;
        private String groupName;
    }

}
