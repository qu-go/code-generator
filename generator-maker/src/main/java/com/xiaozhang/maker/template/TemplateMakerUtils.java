package com.xiaozhang.maker.template;

import cn.hutool.core.util.StrUtil;
import com.xiaozhang.maker.meta.Meta;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author acal1314
 * 模板制作工具类
 */
public class TemplateMakerUtils {

    public static List<Meta.FileConfig.FileInfo> removeGroupFileFromRoot(List<Meta.FileConfig.FileInfo> fileInfoList){
        //获取所有分组的文件配置
        List<Meta.FileConfig.FileInfo> groupFileInfoList = fileInfoList.stream()
            .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
            .collect(Collectors.toList());

        //获取分组内的文件列表
        List<Meta.FileConfig.FileInfo> groupInnerFileInfoList = groupFileInfoList.stream()
            .flatMap(fileInfo -> fileInfo.getFiles().stream())
            .collect(Collectors.toList());
        //获取分组内的所有输入路径
        Set<String> groupSet= groupInnerFileInfoList.stream()
            .map(Meta.FileConfig.FileInfo::getInputPath)
            .collect(Collectors.toSet());

        //排除文件内包含的文件路径
        return fileInfoList.stream()
            .filter(fileInfo -> !groupSet.contains(fileInfo.getInputPath()))
            .collect(Collectors.toList());
    }
}
