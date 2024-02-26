package com.xiaozhang.maker.meta;

/**
 * 数据异常类
 * @author acal1314
 */
public class MetaException extends RuntimeException{
    public MetaException(String msg){
        super(msg);
    }

    public MetaException(String msg,Throwable throwable){
        super(msg,throwable);
    }


}
