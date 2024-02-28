package com.xiaozhang.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.xiaozhang.maker.generator.JarGenerator;
import com.xiaozhang.maker.generator.ScriptGenerator;
import com.xiaozhang.maker.generator.file.DynamicFileGenerator;
import com.xiaozhang.maker.meta.Meta;
import com.xiaozhang.maker.meta.*;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 模板方法模式
 * @author zhang
 */
public abstract class GeneratorTemplate {
    public  void doGenerate() throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManger.getMetaObject();

        //在本项目的根路径下生成
        String projectPath = System.getProperty("user.dir");
        String outRootPath=projectPath+File.separator
            + meta.getFileConfig().getOutputRootPath()+File.separator+meta.getName();
        if (!FileUtil.exist(outRootPath)){
            FileUtil.mkdir(outRootPath);
        }
        //1.把模板文件生成到本项目下
        String sourceCopyDestPath = copySource(meta, outRootPath);
        //2.生成动态文件，代码生成
        generateCode(meta, outRootPath);
        //3.构建jar包
        String jarPath = builderJar(outRootPath, meta);

        //4.script脚本
        String shellPath = generateShell(outRootPath,jarPath);

        //5.生成精装版的项目
        generateDistProject(outRootPath, sourceCopyDestPath, jarPath, shellPath);


    }

    protected  void generateDistProject(String outRootPath, String sourceCopyDestPath, String jarPath, String shellPath) {
        String distOutputPath= outRootPath +"-dist";
        //拷贝jar宝
        String distJarPath=distOutputPath+File.separator+"target";
        FileUtil.mkdir(distJarPath);
        String jarAbsolutePath= outRootPath +File.separator+ jarPath;
        FileUtil.copy(jarAbsolutePath,distJarPath,false);
        //拷贝脚本
        FileUtil.copy(shellPath,distOutputPath,false);
        FileUtil.copy(shellPath +".bat",distOutputPath,false);
        FileUtil.copy(sourceCopyDestPath,distOutputPath,false);
    }

    protected  String generateShell(String outRootPath,String jarPath) {
        String shellPath= outRootPath +File.separator+"generator";
        ScriptGenerator.doGenerate(shellPath,jarPath);
        return shellPath;
    }

    protected  String builderJar(String outRootPath,Meta meta) throws IOException, InterruptedException {
        JarGenerator.doGenerate(outRootPath);
        String jarName=String.format("%s-%s-jar-with-dependencies.jar",meta.getName(),meta.getVersion());
        String jarPath="target/"+jarName;
        return jarPath;
    }

    protected  void generateCode(Meta meta, String outRootPath) throws IOException, TemplateException {
        ClassPathResource classPathResource=new ClassPathResource("");
        String resourcePath = classPathResource.getAbsolutePath();
        //java包的基础路径
        String basePackage = meta.getBasePackage();
        String basePackagePath = StrUtil.join("/", StrUtil.split(basePackage,"."));
        String outputJavaPath= outRootPath +File.separator+"src/main/java/"+basePackagePath;

        String inputPath;
        String outPath;
        //模板的文件路径输出路径
        inputPath=resourcePath+File.separator+ "templates/java/model/DataModel.java.ftl";
        outPath=outputJavaPath+File.separator+"model/DataModel.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/command/ConfigCommand.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/command/ConfigCommand.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/command/GeneratorCommand.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/command/GeneratorCommand.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/command/ListCommand.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/command/ListCommand.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/cli/CommandExecutor.java.ftl";
        outPath=outputJavaPath+File.separator+"cli/CommandExecutor.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/Main.java.ftl";
        outPath=outputJavaPath+File.separator+"Main.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/DynamicFileGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/DynamicFileGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/StaticFileGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/StaticFileGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/MainGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/MainGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/PomGenerator.xml.ftl";
        outPath= outRootPath +File.separator+"pom.xml";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        //生成readme.md
        inputPath=resourcePath+File.separator+"templates/java/generator/ReadmeGenerator.md.ftl";
        outPath= outRootPath +File.separator+"README.md";
        DynamicFileGenerator.generation(inputPath,outPath, meta);

        inputPath=resourcePath+File.separator+ "templates/java/generator/JarGenerator.java.ftl";
        outPath=outputJavaPath+File.separator+"/generator/JarGenerator.java";
        DynamicFileGenerator.generation(inputPath,outPath, meta);
    }

    protected  String copySource(Meta meta, String outRootPath) {
        String sourcePath= meta.getFileConfig().getSourceRootPath();
        String sourceCopyDestPath= outRootPath +File.separator+".source";
        FileUtil.copy(sourcePath,sourceCopyDestPath,false);
        return sourceCopyDestPath;
    }


}
