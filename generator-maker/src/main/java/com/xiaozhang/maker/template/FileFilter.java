package com.xiaozhang.maker.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.xiaozhang.maker.template.enums.FileFilterRangeEnum;
import com.xiaozhang.maker.template.enums.FileFilterRuleEnum;
import com.xiaozhang.maker.template.model.FileFilterConfig;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author acal1314
 */
public class FileFilter {

    /**
     * 单文件过滤
     *
     * @param filterList 过滤规则列表
     * @param file       文件
     * @return 是否保留
     */
    public static boolean doSingleFileFilter(List<FileFilterConfig> filterList, File file) {
        if (CollUtil.isEmpty(filterList)) {
            return true;
        }
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        //默认过滤为true
        boolean result = true;
        for (FileFilterConfig fileFilterConfig : filterList) {
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();
            FileFilterRangeEnum enumByValue = FileFilterRangeEnum.getEnumByValue(range);
            if (enumByValue == null) {
                continue;
            }
            String content = fileName;
            switch (enumByValue) {
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
                    break;
            }
            FileFilterRuleEnum ruleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if (ruleEnum == null) {
                continue;
            }
            switch (ruleEnum) {
                case REGEX:
                    result = content.matches(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                case CONTAINS:
                    result = content.contains(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case STARTS_WITH:
                    result = content.startsWith(value);
                    break;
                default:
                    break;
            }
            if (!result) {
                return false;
            }

        }
        return true;
    }


    /**
     * 对文件或者目录进行过滤
     *
     * @param inputPath        输入文件、目录路径
     * @param filterConfigList 过滤条件
     * @return
     */
    public static List<File> doFilter(String inputPath, List<FileFilterConfig> filterConfigList) {
        List<File> files = FileUtil.loopFiles(inputPath);
        return files.stream()
            .filter(file -> doSingleFileFilter(filterConfigList, file))
            .collect(Collectors.toList());
    }

}
