package com.xiaozhang.generator;

import com.xiaozhang.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author acal1314
 * 本地生成器
 */
public class MainGenerator2 {

    public static  void doGenerator(Object model) throws TemplateException, IOException {
        String inputBasePath="F:\\code\\code-generator\\generator-basic";
        String outPathBasePath="F:\\code\\code-generator\\";
        String inputPath;
        String outPath;

        inputPath = new File(inputBasePath, "src/main/resources/templates/mainTemplate.java.ftl").getAbsolutePath();
        outPath = new File(outPathBasePath, "generated/src/main/resources/templates/mainTemplate.java").getAbsolutePath();
        DynamicGenerator.generation(inputPath,outPath,(MainTemplateConfig) model);
        //生成静态文件
        inputPath = new File(inputBasePath, "generator.bat").getAbsolutePath();
        outPath = new File(outPathBasePath, "generated/generator.bat").getAbsolutePath();
       StaticGenerator.copyFileByHutools(inputPath,outPath);
        //生成动态文件



    }

}
