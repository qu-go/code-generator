package com.xiaozhang.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.xiaozhang.maker.generator.JarGenerator;
import com.xiaozhang.maker.generator.ScriptGenerator;
import com.xiaozhang.maker.generator.file.DynamicFileGenerator;
import com.xiaozhang.maker.meta.Meta;
import com.xiaozhang.maker.meta.MetaManger;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 动态文件生成启动类
 */
public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManger.getMetaObject();
        //在本项目的根路径下生成
        String projectPath = System.getProperty("user.dir");
        String outRootPath=projectPath+File.separator+ meta.getName();
        if (!FileUtil.exist(outRootPath)){
            FileUtil.mkdir(outRootPath);
        }
        ClassPathResource classPathResource=new ClassPathResource("");
        String resourcePath = classPathResource.getAbsolutePath();

        //java包的基础路径
        String basePackage = meta.getBasePackage();
        String basePackagePath = StrUtil.join("/", StrUtil.split(basePackage,"."));
        String outputJavaPath=outRootPath+File.separator+"src/main/java/"+basePackagePath;
        String inputPath;
        String outPath;
        //模板的文件路径输出路径
        inputPath=resourcePath+File.separator+ "templates/java/model/DataModel.java.ftl";
        outPath=outputJavaPath+File.separator+"model/DataModel.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/command/ConfigCommand.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/command/ConfigCommand.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/command/GeneratorCommand.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/command/GeneratorCommand.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/command/ListCommand.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/command/ListCommand.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/CommandExecutor.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/CommandExecutor.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/Main.java.ftl";
        outPath=outputJavaPath+File.separator+"Main.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/DynamicFileGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/DynamicFileGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/StaticFileGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/StaticFileGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/MainGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/MainGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/PomGenerator.xml.ftl";
        outPath=outRootPath+File.separator+"/pom.xml";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/JarGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/JarGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

        JarGenerator.doGenerate(outRootPath);

        String shellPath=outRootPath+File.separator+"generate";
        String jarName=String.format("%s-%s-jar-with-dependencies.jar",meta.getName(),meta.getVersion());
        String jarPath="target/"+jarName;
        ScriptGenerator.doGenerate(shellPath,jarPath);
    }

}
