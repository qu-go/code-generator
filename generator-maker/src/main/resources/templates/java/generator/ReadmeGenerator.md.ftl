# ${name}
> ${name}
> 
> 作者:${author}
> 
>  基于[${author}](网址)的[代码生成器]()制作，谢谢你的使用


可以通过命令行交互输入的方式动态生成想要的项目代码

## 使用说明
执行项目根目录下的脚本文件
> generator <命令> <选项参数>

例子：
> generator generate -l -a -o

<#list modelConfig.models as model>
## 参数说明
1）${model.fieldName}
类型：${model.type}
描述：${model.description}
默认值：${model.defaultValue?c}
缩写：${model.abbr}
</#list>