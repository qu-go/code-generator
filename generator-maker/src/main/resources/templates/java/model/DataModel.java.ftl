package ${basePackage}.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataModel {
    <#list modelConfig.models as model>
    <#if model.description?? || model.fieldName??>
    /**
     *${model.description}
    */
    </#if>
    <#if model.fieldName??>
    private ${model.type} ${model.fieldName} <#if model.defaultValue??>= ${model.defaultValue?c}</#if>;
    </#if>
    </#list>

}
