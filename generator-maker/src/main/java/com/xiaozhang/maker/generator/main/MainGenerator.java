package com.xiaozhang.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.xiaozhang.maker.generator.file.DynamicFileGenerator;
import com.xiaozhang.maker.meta.Meta;
import com.xiaozhang.maker.meta.MetaManger;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        Meta meta = MetaManger.getMetaObject();
        //在本项目的根路径下生成
        String projectPath = System.getProperty("user.dir");
        String outRootPath=projectPath+ File.separator+"generated"+File.separator+ meta.getName();
        if (!FileUtil.exist(outRootPath)){
            FileUtil.mkdir(outRootPath);
        }
        ClassPathResource classPathResource=new ClassPathResource("");
        String resourcePath = classPathResource.getAbsolutePath();

        //java包的基础路径
        String basePackage = meta.getBasePackage();
        String basePackagePath = StrUtil.join("/", StrUtil.split(basePackage,"."));
        String outputJavaPath=outRootPath+File.separator+"src/main/java/"+basePackagePath;

        //模板的文件路径输出路径
        String inputPath=resourcePath+File.separator+ "templates/model/DataModel.java.ftl";
        String outPath=outputJavaPath+File.separator+"/model/DataModel.java";
        DynamicFileGenerator.generation(inputPath,outPath,meta);

    }

}
