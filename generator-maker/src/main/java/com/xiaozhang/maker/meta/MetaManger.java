package com.xiaozhang.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author acal1314
 * 单例模式读取meta
 */
public class MetaManger {

    private static volatile Meta meta;

    private MetaManger(){
        //私有构造防止外部实例化
    }

    public static Meta getMetaObject(){
        //双检测锁
        if (meta==null){
            synchronized (MetaManger.class){
                if (meta==null){
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    private static Meta initMeta(){
        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta meta = JSONUtil.toBean(metaJson, Meta.class);
        return meta;

    }

}
