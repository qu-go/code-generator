package com.xiaozhang.generator;

import com.xiaozhang.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @author acal1314
 * 本地生成器
 */
public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateConfig dataModel = MainTemplateConfig.builder()
            .output("Sum==")
            .loop(false)
            .author("小于").build();
        doGenerator(dataModel);
    }

    public static  void doGenerator(MainTemplateConfig model) throws TemplateException, IOException {
        String property = System.getProperty("user.dir");
        String outPath = new File(property).getParent();
        String inputPath = new File(property, "generator-acm-template-demo").getAbsolutePath();
        //生成静态文件
       StaticGenerator.copyFileRecursive(inputPath,outPath);
        //生成动态文件
        String template = new File(property, "generator-basic/src/main/resources/templates/mainTemplate.java.ftl").getAbsolutePath();
        String output = new File(
            property + File.separator + "generator-acm-template-demo/src/com/yupi/acm/MainTemplate.java")
            .getAbsolutePath();
        System.out.println(template);
        System.out.println(output);
        DynamicGenerator.generation(template,output,model);

    }

}
