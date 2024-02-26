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
 * @author aa
 */
public class MainGenerator extends GeneratorTemplate{

    @Override
    protected void generateDistProject(String outRootPath, String sourceCopyDestPath, String jarPath,
        String shellPath) {
        System.out.println("我不生成了dist");
    }
}
