package ${basePackage}.generator;

import freemarker.template.TemplateException;
import ${basePackage}.model.DataModel;
import java.io.File;
import java.io.IOException;

<#macro generateFile indent fileInfo>
${indent}inputPath = new File(inputBasePath, "${fileInfo.inputPath}").getAbsolutePath();
${indent}outPath = new File(outPathBasePath, "${fileInfo.outputPath}").getAbsolutePath();
<#if fileInfo.generateType == "static">
${indent}StaticFileGenerator.copyFileByHutools(inputPath,outPath);
<#else >
${indent}DynamicFileGenerator.generation(inputPath,outPath,model);
</#if>
</#macro>
/**
 * @author acal1314
 * 本地生成器
 */
public class MainGenerator {

    public static  void doGenerator(DataModel model) throws TemplateException, IOException {
        String inputBasePath="${fileConfig.inputRootPath}";
        String outPathBasePath="${fileConfig.outputRootPath}";
        String inputPath;
        String outPath;
    <#list modelConfig.models as model>
        <#if model.groupKey??>
            <#list model.models as subModel>
                ${subModel.type} ${subModel.fieldName} = model.${model.groupKey}.${subModel.fieldName};
            </#list>
            <#else >
                ${model.type} ${model.fieldName} = model.${model.fieldName};
        </#if>


    </#list>
    <#list fileConfig.files as fileInfo>
        <#if fileInfo.groupKey??>
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
        <#list fileInfo.files as fileInfo>
            <@generateFile indent="            "  fileInfo=fileInfo/>
        </#list>
        }
        <#else >
    <#list fileInfo.files as fileInfo>
        <@generateFile indent="  "  fileInfo=fileInfo/>
    </#list>
        </#if>
        <#else >
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
            <@generateFile indent="            "  fileInfo=fileInfo/>
            }
        <#else >
        <@generateFile indent="        "  fileInfo=fileInfo/>
    </#if>
        </#if>

    </#list>





    }

}
