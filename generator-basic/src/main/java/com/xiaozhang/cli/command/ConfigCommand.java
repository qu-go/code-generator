package com.xiaozhang.cli.command;


import cn.hutool.core.util.ReflectUtil;
import com.xiaozhang.model.MainTemplateConfig;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;

/**
 * @author acal1314
 * 配置字命令
 */
@Command(name = "config" ,description = "查看需要传入动态参数",mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(MainTemplateConfig.class);
        for (Field field : fields) {
            System.out.println(field.getName()+"  类型"+field.getType());
        }
    }
}
