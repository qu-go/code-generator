package com.xiaozhang.generator;

import com.xiaozhang.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author acal1314
 * 生成动态文件
 */
public class DynamicGenerator {

    public static void generation(String inputPath,String outPath,MainTemplateConfig model) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //解决数字显示的时候出现，号,now it will print 1000000
        cfg.setNumberFormat("0.######");
        // 指定模板文件的来源。   这里我设置了一个
        // 普通目录，但非文件系统源也是可能的：
        String parentPath = new File(inputPath).getParent();
        cfg.setDirectoryForTemplateLoading(new File(parentPath));
        // 设置模板文件存储的首选字符集。UTF-8为
        // 在大多数应用中是一个不错的选择：
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        String templateName = new File(inputPath).getName();
        Template temp = cfg.getTemplate(templateName);
        //创建model

        Writer out = new FileWriter(outPath);
        temp.process(model, out);
        out.close();
    }

}
