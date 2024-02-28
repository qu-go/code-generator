package ${basePackage}.cli.command;

import ${basePackage}.model.DataModel;
import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;
<#macro generateArgs indent modelInfo>
${indent}@Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}",</#if>"--${modelInfo.fieldName}"},arity = "0..1",description = "<#if modelInfo.description??>${modelInfo.description}</#if>",interactive = true,echo = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#macro>

<#macro generateCode indent model>
${indent}System.out.println("输入核心模板参数：");
${indent}CommandLine commandLine = new CommandLine(${model.type}.class);
${indent}commandLine.execute(${model.allArgsStr});
</#macro>

/**
 * @author acal1314 生成文件子命令
 */
@Data
@Command(name = "generate", description = "生成文件", mixinStandardHelpOptions = true)
public class GeneratorCommand implements Callable<Integer> {

    <#list modelConfig.models as model>
    <#if model.groupKey??>
    static DataModel.${model.type} ${model.groupKey} =new DataModel.${model.type}();
    @Data
    @Command(name = "${model.groupKey}" )
    static class ${model.type} implements Runnable {
        <#list model.models as modelInfo>
            <@generateArgs indent="        " modelInfo=modelInfo/>
        </#list>

        @Override
        public void run() {
        <#list model.models as subModelInfo>
            ${model.groupKey}.${subModelInfo.fieldName}=${subModelInfo.fieldName};
        </#list>
        }
        }

    <#else >
        <@generateArgs indent="    " modelInfo=model/>
    </#if>
    </#list>

    @Override
    public Integer call() throws Exception {
    <#list modelConfig.models as model>
        <#if model.groupKey??>
        <#if model.condition??>
        if (${model.condition}){
            <@generateCode indent="            " model=model/>
        }
        <#else >
            <@generateCode indent="        " model=model/>
        </#if>
        </#if>
    </#list>
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        <#list modelConfig.models as model>
            <#if model.groupKey??>
                dataModel.${model.groupKey}=${model.groupKey};
            </#if>
        </#list>
        MainGenerator.doGenerator(dataModel);
        System.out.println("生成成功");
        return 0;

    }






}
