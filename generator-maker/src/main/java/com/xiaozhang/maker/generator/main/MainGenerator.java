package com.xiaozhang.maker.generator.main;

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
