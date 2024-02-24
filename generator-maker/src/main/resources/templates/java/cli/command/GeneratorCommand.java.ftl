package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * @author acal1314
 * 生成文件子命令
 */
@Data
@Command(name = "generate",description = "生成文件",mixinStandardHelpOptions = true)
public class GeneratorCommand implements Callable<Integer>{

    <#list modelConfig.models as model>
        <#if model.description?? || model.fieldName??>
            /**
            *${model.description}
            */
        </#if>
        <#if model.fieldName??>
            @Option(names = {"-${model.abbr}","--${model.fieldName}"},arity = "0..1",description = "${model.description}",interactive = true,echo = true)
            private ${model.type} ${model.fieldName} <#if model.defaultValue??>= ${model.defaultValue?c}</#if>;
        </#if>
    </#list>


    @Override
    public Integer call() throws Exception {
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        MainGenerator.doGenerator(dataModel);
        System.out.println("生成成功");
        return 0;
    }

    public String[] check(String[] args) {
        //指令检查
<#--        if (ArrayUtil.contains(args,"generate")){-->
<#--            if (!ArrayUtil.contains(args,"-l")){-->
<#--                args=new String[]{"generate","-l","-a","-o"};-->
<#--            }-->
<#--        }-->
        return args;
    }
}
