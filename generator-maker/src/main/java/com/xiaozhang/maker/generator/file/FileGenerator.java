package com.xiaozhang.maker.generator.file;


import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author acal1314
 * 本地生成器
 */
public class FileGenerator {

    public static  void doGenerator(Object model) throws TemplateException, IOException {
        String property = System.getProperty("user.dir");
        String outPath = new File(property).getParent();
        String inputPath = new File(property, "generator-acm-template-demo").getAbsolutePath();
        //生成静态文件
       StaticFileGenerator.copyFileByHutools(inputPath,outPath);
        //生成动态文件
        String template = new File(property, "generator-maker/src/main/resources/templates/mainTemplate.java.ftl").getAbsolutePath();
        String output = new File(
            outPath + File.separator + "generator-acm-template-demo/src/com/yupi/acm/MainTemplate.java")
            .getAbsolutePath();
        DynamicFileGenerator.generation(template,output,model);

    }

}
