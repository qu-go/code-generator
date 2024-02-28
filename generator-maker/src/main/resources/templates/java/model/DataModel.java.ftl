package ${basePackage}.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#macro generateModel indent model>
    <#if model.description?? || model.fieldName??>
        /**
        *${indent}${model.description}
        */
    <#if model.fieldName??>
        public ${model.type} ${model.fieldName} <#if model.defaultValue??>= ${model.defaultValue?c}</#if>;
    </#if>
    </#if>
</#macro>
/**
 * 数据模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataModel {
    <#list modelConfig.models as model>
    <#if model.groupKey??>

        public ${model.type} ${model.groupKey}=new ${model.type}();

        @Data
        public static class ${model.type} {
        <#list model.models as model>
            <@generateModel indent="   " model=model/>
        </#list>

        }
        <#else >
        <@generateModel indent="   " model=model/>
    </#if>

    </#list>

}
