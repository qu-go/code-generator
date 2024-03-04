package com.xiaozhang.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xiaozhang.maker.meta.Meta;
import com.xiaozhang.maker.meta.enums.FileGenerateTypeEnum;
import com.xiaozhang.maker.meta.enums.FileTypeEnum;
import com.xiaozhang.maker.template.enums.FileFilterRangeEnum;
import com.xiaozhang.maker.template.enums.FileFilterRuleEnum;
import com.xiaozhang.maker.template.model.*;


import java.io.File;
import java.io.FileReader;
import java.nio.file.CopyOption;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author acal1314
 */
public class TemplateMaker {

    public static void main(String[] args) {
        Meta meta = new Meta();
        meta.setName("template-demo");
        meta.setDescription("代码生成器");

        //1.输入信息
        //2.输入文件信息(项目生成代码的模板文件)
        //F:\code\springboot-init\springboot-init
        String projectRootPath = System.getProperty("user.dir");
//        String originRootPath = new File(projectRootPath).getParent() + File.separator + "generator-acm-template-demo";
//        String fileInputPath = "src/com/yupi/acm/mainTemplate.java";
        String originRootPath = new File(projectRootPath).getParent() + File.separator + "springboot-init";
        String fileInputPath = "src/main/java/com/yupi/springbootinit/common";
        //String fileInputPath = "src/main/java/com/yupi/springbootinit/constant/UserConstant.java";

        //3.输入模型信息
//        Meta.ModelConfig.Models modelInfo = new Meta.ModelConfig.Models();
//        modelInfo.setType("String");
//        modelInfo.setFieldName("outputText");
//        modelInfo.setDefaultValue("Sum==");
        //第二次
        Meta.ModelConfig.Models modelInfo = new Meta.ModelConfig.Models();
        modelInfo.setType("String");
        modelInfo.setFieldName("Code");

//        String searchStr="Sum:";
        String searchStr = "code";
        List<String> inputFileList = new ArrayList<>();
        inputFileList.add(fileInputPath);
        inputFileList.add("src/main/java/com/yupi/springbootinit/common/DeleteRequest.java");
        //String input2 = "src/main/java/com/yupi/springbootinit/controller";
        //测试分组合并
        String input2 = "src/main/resources/application.yml";
//        测试第一次生成，支持输入一个文件路径或者目录
//        long id = makeTemplate(meta, originRootPath, fileInputPath, searchStr,null,modelInfo);
        //   long id = makeTemplate(meta, originRootPath, fileInputPath, searchStr, 1763652736201453568L, modelInfo);
//        测试第一次生成，支持输入多个文件生成；
        // long id = makeTemplate(meta, originRootPath, inputFileList, searchStr, 1763686188166672384L, modelInfo);
        //  System.out.println(id);

        //测试文件过滤
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig = new TemplateMakerFileConfig.FileInfoConfig();
        FileFilterConfig fileFilterConfig = FileFilterConfig.builder().range(FileFilterRangeEnum.FILE_NAME.getValue())
            .rule(FileFilterRuleEnum.CONTAINS.getValue())
            .value("Base").build();
        List<FileFilterConfig> list = new ArrayList<>();
        fileInfoConfig.setPath(fileInputPath);
        list.add(fileFilterConfig);
        fileInfoConfig.setFilterConfigList(list);
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(input2);
        templateMakerFileConfig.setFiles(Arrays.asList(fileInfoConfig, fileInfoConfig1));

        //构建组信息
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setCondition("outputText");
        fileGroupConfig.setGroupKey("test");
        fileGroupConfig.setGroupName("测试分组");
        templateMakerFileConfig.setFileGroupConfig(fileGroupConfig);

        //模型配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
//        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
//        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);
//        modelGroupConfig.setGroupKey("mysql");
//        modelGroupConfig.setGroupName("数据库配置");

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

        long id = makeTemplate(meta, originRootPath, templateMakerModelConfig, templateMakerFileConfig,null, 1764249133955100672L);
        System.out.println(id);
    }

    private static List<Meta.ModelConfig.Models> distinctModelList(List<Meta.ModelConfig.Models> modelsList) {
        //同组去重，其他保留去重
        //1.有分组的
        Map<String, List<Meta.ModelConfig.Models>> groupKeyModelMap = modelsList.stream()
            .filter(modelInfo -> StrUtil.isNotBlank(modelInfo.getGroupKey()))
            .collect(Collectors.groupingBy(Meta.ModelConfig.Models::getGroupKey));
        //保存每个组的合并对象
        Map<String, Meta.ModelConfig.Models> modelMergeMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfig.Models>> entry : groupKeyModelMap.entrySet()) {
            List<Meta.ModelConfig.Models> tempFileInfoList = entry.getValue();
            //去重
            List<Meta.ModelConfig.Models> newModelInfoList = new ArrayList<>(tempFileInfoList.stream()
                .flatMap(modelInfo -> modelInfo.getModels().stream())
                .collect(Collectors.toMap(Meta.ModelConfig.Models::getFieldName,
                    o -> o, (e, r) -> r)).values());
            //使用新的groupKey配置
            Meta.ModelConfig.Models newModelInfo = CollUtil.getLast(tempFileInfoList);
            newModelInfo.setModels(newModelInfoList);
            String groupKey = entry.getKey();
            modelMergeMap.put(groupKey, newModelInfo);

        }
        //添加已经分组的配置
        List<Meta.ModelConfig.Models> resultModelInfoList=new ArrayList<>(modelMergeMap.values());
        //3.添加没有分组的文件
        Collection<Meta.ModelConfig.Models> noGroupFileInfoCollection = modelsList.stream()
            .filter(modelInfo -> StrUtil.isBlank(modelInfo.getGroupKey()))
            .collect(Collectors.toMap(Meta.ModelConfig.Models::getFieldName
                , o -> o, (e, r) -> r)).values();
        resultModelInfoList.addAll(new ArrayList<>(noGroupFileInfoCollection));
        return resultModelInfoList;
    }


    //文件去重，和分组
    private static List<Meta.FileConfig.FileInfo> distinctFileInfo(List<Meta.FileConfig.FileInfo> fileInfoList) {
        //同组去重，其他保留去重
        //1.有分组的
        Map<String, List<Meta.FileConfig.FileInfo>> groupKeyFileMap = fileInfoList.stream()
            .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
            .collect(Collectors.groupingBy(Meta.FileConfig.FileInfo::getGroupKey));
        //保存每个组的合并对象
        Map<String, Meta.FileConfig.FileInfo> fileMergeMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfig.FileInfo>> entry : groupKeyFileMap.entrySet()) {
            List<Meta.FileConfig.FileInfo> tempFileInfoList = entry.getValue();
            //去重
            ArrayList<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream()
                .flatMap(fileInfo -> fileInfo.getFiles().stream())
                .collect(Collectors.toMap(Meta.FileConfig.FileInfo::getOutputPath,
                    o -> o, (e, r) -> r)).values());
            //使用新的groupKey配置
            Meta.FileConfig.FileInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKey = entry.getKey();
            fileMergeMap.put(groupKey, newFileInfo);

        }
        //添加已经分组的配置
        List<Meta.FileConfig.FileInfo> resultFileInfoList=new ArrayList<>(fileMergeMap.values());
        //3.添加没有分组的文件
        Collection<Meta.FileConfig.FileInfo> noGroupFileInfoCollection = fileInfoList.stream()
            .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
            .collect(Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath
                , o -> o, (e, r) -> r)).values();
        resultFileInfoList.addAll(new ArrayList<>(noGroupFileInfoCollection));

        return resultFileInfoList;
    }

    /**
     *
     * @param sourceRootPath 项目的根目录
     * @param inputFile      文件的对象（获取绝对路径）
     * @return
     */
    private static Meta.FileConfig.FileInfo makeFileTemplate(TemplateMakerModelConfig templateMakerModelConfig,
        String sourceRootPath, File inputFile) {
        boolean containsSource = sourceRootPath.contains("\\\\");
        if (containsSource) {
            sourceRootPath = sourceRootPath.replace("\\\\", "/");
        } else {
            sourceRootPath = sourceRootPath.replace("\\", "/");
        }
        boolean contains = inputFile.getAbsolutePath().contains("\\\\");
        String fileInputAbsolutePath = null;
        if (contains) {
            fileInputAbsolutePath = inputFile.getAbsolutePath().replace("\\\\", "/");
        } else {
            fileInputAbsolutePath = inputFile.getAbsolutePath().replace("\\", "/");
        }

        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";
        String fileInput = fileInputAbsolutePath.replace(sourceRootPath + "/", "");
        String fileOutput = fileInput + ".ftl";
        //如果不是第一次，直接读取模板
        String fileContent = null;
        boolean existTemplateFile = FileUtil.exist(fileOutputAbsolutePath);
        if (existTemplateFile) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        //支持多个模型对一个文件多伦替换
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String newFileContent=fileContent;
        String replacement = null;
        for (TemplateMakerModelConfig.ModelInfo model : templateMakerModelConfig.getModels()) {
            //没有分组
            if (modelGroupConfig==null){
                replacement = String.format("${%s}", model.getFieldName());
            }else{
                replacement = String.format("${%s.%s}",modelGroupConfig.getGroupKey(),model.getFieldName());
            }
             newFileContent = StrUtil.replace(newFileContent, model.getReplaceText(), replacement);
        }
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileOutput);
        fileInfo.setOutputPath(fileInput);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
        boolean contentEquals = newFileContent.equals(fileContent);
        if (!existTemplateFile){
            //内容相等，表示没有修改
            if (contentEquals) {
                //输入的路径没有ftl后缀
                fileInfo.setOutputPath(fileInput);
                fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
            }else {
                FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
            }

        }else if (!contentEquals){
            //有模板文件，并且增加了新坑，直接生成
            FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
        }


        return fileInfo;
    }


    /**
     * @param newMeta                 模板文件对象
     * @param originProjectPath       源文件跟路径
     * @param templateMakerFileConfig 源文件相对路径列表和规则
     * @param id                      隔离空间的id
     * @return
     */
    public static long makeTemplate(Meta newMeta, String originProjectPath,
        TemplateMakerModelConfig templateMakerModelConfig, TemplateMakerFileConfig templateMakerFileConfig,
        TemplateOutputConfig templateOutputConfig,Long id) {
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }
        //后续逻辑
        //1.输入信息
        //2.输入文件信息(项目生成代码的模板文件)
        String projectRootPath = System.getProperty("user.dir");
        //复制元项目在.temp
        String tempDirPath = projectRootPath + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;
        //第一次生成
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }
        //文件追加功能
        String sourceRootPath = FileUtil.loopFiles(new File(templatePath), 1, null).stream()
            .filter(FileUtil::isDirectory)
            .findFirst()
            .orElseThrow(RuntimeException::new)
            .getAbsolutePath();
        sourceRootPath = sourceRootPath.replace("\\", "/");
        //生成配置文件
        //修改配置文件代码为项目同目录，否则会把meta文件也当做生成模板文件
        String metaPath = templatePath + File.separator + "meta.json";
        List<Meta.FileConfig.FileInfo> fileInfoList = makeFileTemplates(templateMakerModelConfig,
            templateMakerFileConfig, sourceRootPath);
        List<Meta.ModelConfig.Models> newModelList = getModelsList(templateMakerModelConfig);
        //如果又meta文件说明不是第一次生成
        if (FileUtil.exist(metaPath)) {
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaPath), Meta.class);
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;
            //增加或者修改
            List<Meta.FileConfig.FileInfo> files = newMeta.getFileConfig().getFiles();
            files.addAll(fileInfoList);
            List<Meta.ModelConfig.Models> models = newMeta.getModelConfig().getModels();
            models.addAll(newModelList);
            //去重
            newMeta.getModelConfig().setModels(distinctModelList(models));
            newMeta.getFileConfig().setFiles(distinctFileInfo(files));

        } else {

            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfig.FileInfo> fileInfos = new ArrayList<>();
            fileConfig.setFiles(fileInfos);
            fileInfos.addAll(fileInfoList);

            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfig.Models> modelsList = new ArrayList<>();
            modelConfig.setModels(modelsList);
            modelsList.addAll(newModelList);

        }

        //从为分组的文件中移除组内的已有文件
        if (templateOutputConfig!=null){
            if (templateOutputConfig.isRemoveGroupFileFromRoot()){
                List<Meta.FileConfig.FileInfo> fileList = newMeta.getFileConfig().getFiles();
                newMeta.getFileConfig().setFiles(TemplateMakerUtils.removeGroupFileFromRoot(fileList));
            }
        }
        //输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaPath);
        return id;

    }

    private static List<Meta.ModelConfig.Models> getModelsList(TemplateMakerModelConfig templateMakerModelConfig) {
        //处理模型组
        //创建一个新的容器装新的模型数据
        List<Meta.ModelConfig.Models> newModelList=new ArrayList<>();
        if (templateMakerModelConfig==null){
            return newModelList;
        }
        List<TemplateMakerModelConfig.ModelInfo> modelMakerList = templateMakerModelConfig.getModels();
        if (CollUtil.isEmpty(modelMakerList)){
            return newModelList;
        }
        List<Meta.ModelConfig.Models> inputModelList = modelMakerList.stream()
            .map(modelInfo1 -> {
                Meta.ModelConfig.Models model = new Meta.ModelConfig.Models();
                BeanUtil.copyProperties(modelInfo1, model);
                return model;
            }).collect(Collectors.toList());

        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        //如果是模型组
        if (modelGroupConfig!=null){
            String condition = modelGroupConfig.getCondition();
            String groupKey = modelGroupConfig.getGroupKey();
            String groupName = modelGroupConfig.getGroupName();
            Meta.ModelConfig.Models groupModel = new Meta.ModelConfig.Models();
            groupModel.setGroupKey(groupKey);
            groupModel.setCondition(condition);
            groupModel.setGroupName(groupName);
            groupModel.setModels(inputModelList);
            newModelList.add(groupModel);
        }else{
            newModelList.addAll(inputModelList);
        }
        return newModelList;
    }

    private static List<Meta.FileConfig.FileInfo> makeFileTemplates(TemplateMakerModelConfig templateMakerModelConfig,
        TemplateMakerFileConfig templateMakerFileConfig, String sourceRootPath) {

        List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
        if (templateMakerFileConfig==null){
            return fileInfoList;
        }
        List<TemplateMakerFileConfig.FileInfoConfig> templateConfigList = templateMakerFileConfig.getFiles();
        if (CollUtil.isEmpty(templateConfigList)){
            return fileInfoList;
        }
        for (TemplateMakerFileConfig.FileInfoConfig templateConfig : templateConfigList) {
            String inputFilePath = templateConfig.getPath();
            //如果不是绝对路径拼接成绝对路径
            if (!inputFilePath.startsWith(sourceRootPath)) {
                inputFilePath = sourceRootPath + File.separator + inputFilePath;
            }
            List<File> files = FileFilter.doFilter(inputFilePath, templateConfig.getFilterConfigList());
            //不处理已经生成ftl文件模板的文件
            files=  files.stream()
                .filter(file -> !file.getName().endsWith(".ftl"))
                .collect(Collectors.toList());
            for (File file : files) {
                Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(templateMakerModelConfig, sourceRootPath, file);
                fileInfoList.add(fileInfo);
            }
        }

        //如果是文件组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if (fileGroupConfig != null) {
            String condition = fileGroupConfig.getCondition();
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
            fileInfo.setGroupKey(groupKey);
            fileInfo.setType(FileTypeEnum.GROUP.getValue());
            fileInfo.setCondition(condition);
            fileInfo.setGroupName(groupName);

            //把所有文件分在一个组里面
            fileInfo.setFiles(fileInfoList);
            fileInfoList = new ArrayList<>();
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }


    public static long makeTemplate(TemplateMakerConfig templateMakerConfig){
        Long id = templateMakerConfig.getId();
        Meta mete = templateMakerConfig.getMete();
        String originProjectPath = templateMakerConfig.getOriginProjectPath();
        TemplateOutputConfig templateOutputConfig = templateMakerConfig.getTemplateOutputConfig();
        TemplateMakerFileConfig templateMakerFileConfig = templateMakerConfig.getFileConfig();
        TemplateMakerModelConfig templateMakerModelConfig = templateMakerConfig.getModelConfig();
        long returnId = makeTemplate(mete, originProjectPath, templateMakerModelConfig, templateMakerFileConfig,templateOutputConfig, id);
        return returnId;
    }
}
