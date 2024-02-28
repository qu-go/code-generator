package com.xiaozhang.maker.meta;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author acal1314
 * 元数据
 */
@NoArgsConstructor
@Data
public class Meta {

    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfig fileConfig;
    private ModelConfig modelConfig;

    @NoArgsConstructor
    @Data
    public static class FileConfig {

        private String inputRootPath;
        private String outputRootPath;
        private String sourceRootPath;
        private String type;
        private List<FileInfo> files;

        @NoArgsConstructor
        @Data
        public static class FileInfo {

            private String groupKey;
            private String groupName;
            private String type;
            private String condition;
            private List<FileInfo> files;
            private String inputPath;
            private String outputPath;
            private String generateType;


        }
    }

    @NoArgsConstructor
    @Data
    public static class ModelConfig {

        private List<Models> models;

        @NoArgsConstructor
        @Data
        public static class Models {

            private String fieldName;
            private String type;
            private String description;
            private Object defaultValue;
            private String abbr;
            private String groupKey;
            private String groupName;
            private String condition;
            private List<Models> models;

            //拼接所有的参数
            private String allArgsStr;


        }
    }
}
