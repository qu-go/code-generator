package com.xiaozhang.maker.generator.file;

import cn.hutool.core.io.FileUtil;
import com.xiaozhang.maker.model.DataModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author acal1314
 * 生成动态文件
 */
public class DynamicFileGenerator {

    /**
     *
     * @param inputPath 模板文件路径
     * @param outPath 输出的模板文件路径,包含文件的名字
     * @param model 动态绑定的数据
     * @throws IOException
     * @throws TemplateException
     */
    public static void generation(String inputPath,String outPath, Object model) throws IOException, TemplateException {
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
        //判断输出路径不为空
        if(!FileUtil.exist(outPath)){
            FileUtil.touch(outPath);
        }
        //创建model
        Writer out = new FileWriter(outPath);
        temp.process(model, out);
        out.close();
    }

}
