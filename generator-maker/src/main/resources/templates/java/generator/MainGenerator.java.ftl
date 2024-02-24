package ${basePackage}.generator;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author acal1314
 * 本地生成器
 */
public class MainGenerator {

    public static  void doGenerator(Object model) throws TemplateException, IOException {
        String inputBasePath="${fileConfig.inputRootPath}";
        String outPathBasePath="${fileConfig.outputRootPath}";
        String inputPath;
        String outPath;
        <#list fileConfig.files as fileInfo>
            inputPath = new File(inputBasePath, "${fileInfo.inputPath}").getAbsolutePath();
            outPath = new File(outPathBasePath, "${fileInfo.outputPath}").getAbsolutePath();

            <#if fileInfo.generateType == "static">
                StaticFileGenerator.copyFileByHutools(inputPath,outPath);
                <#else >
                    DynamicFileGenerator.generation(inputPath,outPath,model);
            </#if>


        </#list>





    }

}
