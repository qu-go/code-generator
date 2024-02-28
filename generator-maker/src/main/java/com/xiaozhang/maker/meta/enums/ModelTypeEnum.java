package com.xiaozhang.maker.meta.enums;


/**
 * 文件类型没见
 * @author acal1314
 */


public enum ModelTypeEnum {
    /**
     *
     */
    STRING("字符串","String"),
    /**
     *
     */
    BOOLEAN("布尔","boolean");

    private final String text;
    /**
     * 文件值
     */
    private final String value;

    ModelTypeEnum(String text, String value) {
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
