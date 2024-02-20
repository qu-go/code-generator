package com.xiaozhang.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.*;

/**
 * freemaker 快速入门
 * @author acal1314
 */
public class FreeMakerDemo {

    public static void main(String[] args) throws TemplateException, IOException {
        configurationInit();
    }

    public static void configurationInit() throws IOException, TemplateException {
        // 创建您的配置实例，并指定是否达到 FreeMarker 的要求
        // 版本（此处为 2.3.32）是否要应用非 100% 的修复
        // 向下兼容。   有关详细信息，请参阅配置 JavaDoc。
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //解决数字显示的时候出现，号,now it will print 1000000
        cfg.setNumberFormat("0.######");
        // 指定模板文件的来源。   这里我设置了一个
        // 普通目录，但非文件系统源也是可能的：
        cfg.setDirectoryForTemplateLoading(new File("freeMaker-fast-demo/src/main/resources/templates"));

        // 从这里我们将为新项目设置推荐的设置。   这些
        // 不是向后兼容性的默认值。

        // 设置模板文件存储的首选字符集。UTF-8为
        // 在大多数应用中是一个不错的选择：
        cfg.setDefaultEncoding("UTF-8");

        // 设置错误的显示方式。
        // 在网页*开发*期间 TemplateExceptionHandler.HTML_DEBUG_HANDLER 更好。
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 不要在 FreeMarker 中记录异常，否则它无论如何都会向你抛出异常：
        cfg.setLogTemplateExceptions(false);

        // 将模板处理期间抛出的未经检查的异常包装到 TemplateException-s 中：
        cfg.setWrapUncheckedExceptions(true);
        Template temp = cfg.getTemplate("myweb.html.ftl");
        //创建model
        Map<String,Object> dataModel=new HashMap<>();
        dataModel.put("currentYear", 2001);
        List<Map<String,Object>> items=new ArrayList<>();
        Map<String,Object> item1=new HashMap<>();
        item1.put("url","https://codefater.cn");
        item1.put("title","简历模板页面");
        Map<String,Object> item2=new HashMap<>();
        item2.put("url","https://codefater.cn");
        item2.put("title","个人中心页面");
        items.add(item1);
        items.add(item2);
        dataModel.put("menuItems",items);
        dataModel.put("user","xiaozhang");

        Writer out = new FileWriter("myweb.html");
        temp.process(dataModel, out);
        out.close();
    }




}
