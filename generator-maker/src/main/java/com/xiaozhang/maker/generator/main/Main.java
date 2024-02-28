package com.xiaozhang.maker.generator.main;

import com.xiaozhang.maker.model.DataModel;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * 测试类
 * @author aa
 */
public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}
