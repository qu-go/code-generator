package com.xiaozhang.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.xiaozhang.maker.meta.Meta;
import com.xiaozhang.maker.template.enums.FileFilterRangeEnum;
import com.xiaozhang.maker.template.enums.FileFilterRuleEnum;
import com.xiaozhang.maker.template.model.FileFilterConfig;
import com.xiaozhang.maker.template.model.TemplateMakerConfig;
import com.xiaozhang.maker.template.model.TemplateMakerFileConfig;
import com.xiaozhang.maker.template.model.TemplateMakerModelConfig;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TemplateMakerTest {

    /**
     * 测试同配置生成时候，强制变成静态
     */
    @Test
    public void makeTemplateBug1() {
        Meta meta = new Meta();
        meta.setName("template-demo");
        meta.setDescription("代码生成器");

        String projectRootPath = System.getProperty("user.dir");
        String originRootPath = new File(projectRootPath).getParent() + File.separator + "springboot-init";
        String fileInputPath = "src/main/resources/application.yml";


        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig.setPath(fileInputPath);
        templateMakerFileConfig.setFiles(Collections.singletonList(fileInfoConfig));

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);
        modelGroupConfig.setGroupKey("mysql");
        modelGroupConfig.setGroupName("数据库配置");

        TemplateMakerModelConfig.ModelInfo modelInfo1 = new TemplateMakerModelConfig.ModelInfo();
        modelInfo1.setFieldName("url");
        modelInfo1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfo1.setReplaceText("jdbc:mysql://localhost:3306/my_db");
        modelInfo1.setType("String");

        TemplateMakerModelConfig.ModelInfo modelInfo2 = new TemplateMakerModelConfig.ModelInfo();
        modelInfo2.setFieldName("username");
        modelInfo2.setDefaultValue("root");
        modelInfo2.setReplaceText("root");
        modelInfo2.setType("String");
        templateMakerModelConfig.setModels(Arrays.asList(modelInfo1,modelInfo2));

        long id = TemplateMaker.makeTemplate(meta, originRootPath, templateMakerModelConfig, templateMakerFileConfig,null, 1764266152540729344L);
        System.out.println(id);
    }

    /**
     * 同文件目录多次生成，会扫描新的ftl文件
     */
    @Test
    public void testMakeTemplateBug2(){
        Meta meta = new Meta();
        meta.setName("template-demo");
        meta.setDescription("代码生成器");

        List<File> files = FileUtil.loopFiles(
            "F:/code/code-generator/generator-maker/.temp/1764266152540729344/springboot-init/src/main/java/com/yupi/springbootinit/common");

        String projectRootPath = System.getProperty("user.dir");
        String originRootPath = new File(projectRootPath).getParent() + File.separator + "springboot-init";
      //  String fileInputPath = "src/main/java/com/yupi/springbootinit/common";
        String fileInputPath = "./";

        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig.setPath(fileInputPath);
        templateMakerFileConfig.setFiles(Collections.singletonList(fileInfoConfig));

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();

        TemplateMakerModelConfig.ModelInfo modelInfo1 = new TemplateMakerModelConfig.ModelInfo();
        modelInfo1.setFieldName("className");
        modelInfo1.setReplaceText("BaseResponse");
        modelInfo1.setType("String");

        templateMakerModelConfig.setModels(Collections.singletonList(modelInfo1));

        long id = TemplateMaker.makeTemplate(meta, originRootPath, templateMakerModelConfig, templateMakerFileConfig,null, 1764266152540729344L);
        System.out.println(id);
    }

    @Test
    public void jsonInMakerConfig(){
        String s = ResourceUtil.readUtf8Str("TemplateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(s, TemplateMakerConfig.class);

        TemplateMaker.makeTemplate(templateMakerConfig);
    }

    @Test
    public void generateSpringbootInit(){
        String rootUrl="examples/springboot-init";
        String s = ResourceUtil.readUtf8Str(rootUrl+File.separator+"TemplateMaker.json");
        TemplateMakerConfig templateMakerConfig = JSONUtil.toBean(s, TemplateMakerConfig.class);

        TemplateMaker.makeTemplate(templateMakerConfig);

        String s2 = ResourceUtil.readUtf8Str(rootUrl+File.separator+"TemplateMaker1.json");
        TemplateMakerConfig templateMakerConfig1 = JSONUtil.toBean(s2, TemplateMakerConfig.class);

        TemplateMaker.makeTemplate(templateMakerConfig1);
        String s3 = ResourceUtil.readUtf8Str(rootUrl+File.separator+"TemplateMaker2.json");
        TemplateMakerConfig templateMakerConfig3 = JSONUtil.toBean(s3, TemplateMakerConfig.class);

        TemplateMaker.makeTemplate(templateMakerConfig3);
    }
}