package com.xiaozhang.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaozhang.maker.meta.enums.FileGenerateTypeEnum;
import com.xiaozhang.maker.meta.enums.FileTypeEnum;
import com.xiaozhang.maker.meta.enums.ModelTypeEnum;
import freemarker.template.utility.StringUtil;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 校验数据参数
 *
 * @author acal1314
 */
public class MetaValidator {

    public static void doValidAndFill(Meta meta) {

        validRootMeta(meta);
        validFileConfig(meta);
        validModelConfig(meta);


    }

    private static void validModelConfig(Meta meta) {
        Meta.ModelConfig modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        List<Meta.ModelConfig.Models> models = modelConfig.getModels();
        if (!CollectionUtil.isNotEmpty(models)) {
            return;
        }
        for (Meta.ModelConfig.Models model : models) {
            String fieldName = model.getFieldName();
            if (StrUtil.isBlank(fieldName)) {
                throw new MetaException("未填写fieldName");
            }
            String type = StrUtil.blankToDefault(model.getType(), ModelTypeEnum.STRING.getValue());
            model.setType(type);
        }
    }

    private static void validFileConfig(Meta meta) {
        Meta.FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)) {
            throw new MetaException("模板原始路径sourceRootPath不能为空");
        }
        String inputRootPath = StrUtil.blankToDefault(fileConfig.getInputRootPath()
            , ".source" + File.separator + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).toString());
        fileConfig.setInputRootPath(inputRootPath);
        String outputRootPath = StrUtil.blankToDefault(fileConfig.getOutputRootPath(), "generated");
        fileConfig.setOutputRootPath(outputRootPath);
        String type = StrUtil.blankToDefault(fileConfig.getType(),FileTypeEnum.DIR.getValue());
        fileConfig.setType(type);
        List<Meta.FileConfig.FileInfo> files = fileConfig.getFiles();
        if (!CollectionUtil.isNotEmpty(files)) {
            return;
        }
        for (Meta.FileConfig.FileInfo fileInfo : files) {
            String inputPath = fileInfo.getInputPath();
            if (StrUtil.isEmpty(inputPath)) {
                throw new MetaException("没有填写inputPath");
            }
            String outputPath = StrUtil.blankToDefault(fileInfo.getOutputPath(), inputPath);
            fileInfo.setOutputPath(outputPath);
            String infoType = fileInfo.getType();
            if (StrUtil.isBlank(infoType)) {
                if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))) {
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                } else {
                    fileInfo.setType(FileTypeEnum.FILE.getValue());
                }
            }
            String generateType = fileInfo.getGenerateType();
            if (StrUtil.isBlank(generateType)) {
                if (inputPath.endsWith(".ftl")) {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                } else {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                }
            }
        }
    }

    private static void validRootMeta(Meta meta) {
        String name = StrUtil.blankToDefault(meta.getName(), "my-project");
        meta.setName(name);
        String description = StrUtil.blankToDefault(meta.getDescription(), "我的代码生成器");
        meta.setDescription(description);
        String version = StrUtil.blankToDefault(meta.getVersion(), "1.0");
        meta.setVersion(version);
        String author = StrUtil.blankToDefault(meta.getAuthor(), "yupi");
        meta.setAuthor(author);
        String createTime = StrUtil.blankToDefault(meta.getCreateTime(), DateUtil.now());
        meta.setCreateTime(createTime);
    }
}
