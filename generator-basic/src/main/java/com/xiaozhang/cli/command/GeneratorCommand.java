package com.xiaozhang.cli.command;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.xiaozhang.generator.MainGenerator;
import com.xiaozhang.model.MainTemplateConfig;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * @author acal1314
 * 生成文件子命令
 */
@Data
@Command(name = "generate",description = "生成文件",mixinStandardHelpOptions = true)
public class GeneratorCommand implements Callable<Integer> ,CommandInterface {

    @Option(names = {"-l","--loop"},arity = "0..1",description = "是否循环",interactive = true,echo = true)
    private boolean loop;

    @Option(names = {"-a","--author"},arity = "0..1",description = "作者",interactive = true,echo = true)
    private String author;

    @Option(names = {"-o","--output"},arity = "0..1",description = "输出",interactive = true,echo = true)
    private String output;


    @Override
    public Integer call() throws Exception {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        BeanUtil.copyProperties(this,mainTemplateConfig);
        MainGenerator.doGenerator(mainTemplateConfig);
        System.out.println("生成成功");
        return 0;
    }

    @Override
    public String[] check(String[] args) {
        //指令检查
        if (ArrayUtil.contains(args,"generate")){
            if (!ArrayUtil.contains(args,"-l")){
                args=new String[]{"generate","-l","-a","-o"};
            }
        }
        return args;
    }
}
