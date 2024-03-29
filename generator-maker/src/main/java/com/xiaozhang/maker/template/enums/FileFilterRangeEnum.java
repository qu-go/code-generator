package com.xiaozhang.maker.template.enums;


import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

/**
 * 文件类型没见
 * @author acal1314
 */

@Getter
public enum FileFilterRangeEnum {
    /**
     *
     */
    FILE_NAME("文件名称","fileName"),
    /**
     *
     */
    FILE_CONTENT("文件内容","fileContent");

    private final String text;

    private final String value;

    FileFilterRangeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public static FileFilterRangeEnum getEnumByValue(String value){
        if (ObjectUtil.isEmpty(value)){
            return null;
        }
        FileFilterRangeEnum[] values = FileFilterRangeEnum.values();
        for (FileFilterRangeEnum fileFilterRangeEnum : values) {
            if (fileFilterRangeEnum.value.equals(value)){
                return fileFilterRangeEnum;
            }
        }
        return null;
    }

}
