package com.xiaozhang.maker.template.enums;


import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 文件类型没见
 * @author acal1314
 */

@Getter
public enum FileFilterRuleEnum {
    /**
     *
     */
    CONTAINS("包含","contains"),
    /**
     *
     */
    ENDS_WITH("后缀匹配","endsWith"),
    /**
     *
     */
    REGEX("正则","regex"),
    /**
     *
     */
    EQUALS("相等","equals"),
    /**
     *
     */
    STARTS_WITH("前缀匹配","startsWith");

    private final String text;

    private final String value;

    FileFilterRuleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public static FileFilterRuleEnum getEnumByValue(String value){
        if (ObjectUtil.isEmpty(value)){
            return null;
        }
        FileFilterRuleEnum[] values = FileFilterRuleEnum.values();
        for (FileFilterRuleEnum fileFilterRangeEnum : values) {
            if (fileFilterRangeEnum.value.equals(value)){
                return fileFilterRangeEnum;
            }
        }
        return null;
    }

}
