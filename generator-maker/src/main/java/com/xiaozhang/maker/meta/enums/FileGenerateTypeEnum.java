package com.xiaozhang.maker.meta.enums;


/**
 * 文件类型没见
 * @author acal1314
 */


public enum FileGenerateTypeEnum {
    /**
     *
     */
    STATIC("静态","static"),
    /**
     *
     */
    DYNAMIC("动态","dynamic");

    private final String text;

    private final String value;

    FileGenerateTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
