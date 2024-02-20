package com.xiaozhang.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 一个文件拷贝到另一个文件
 * @author acal1314
 */
public class StaticGenerator {

    public static void main(String[] args) {
        //获取项目的目录文件
        String property = System.getProperty("user.dir");
        String parentPath = new File(property).getParent();
        String inputPath = new File(property, "generator-acm-template-demo").getAbsolutePath();
        String outputPath=parentPath;
       // copyFileByHutools(inputPath,outputPath);
        copyFileRecursive(inputPath,outputPath);

    }


    public static void copyFileByHutools(String inputPath ,String outPath){
        File copy = FileUtil.copy(inputPath, outPath, false);

    }


    public static void copyFileRecursive(String  inputPath,String outPath){
        File inputFile = new File(inputPath);
        File outFile = new File(outPath);
        try {
            copyFileRecursive(inputFile,outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void copyFileRecursive(File  inputFile,File outFile) throws IOException {
        //判断是否是目录
        if (inputFile.isDirectory()){
            File destOutFile = new File(outFile, inputFile.getName());
            //生成目标文件
            if (!destOutFile.exists()){
                destOutFile.mkdirs();
            }
            File[] files = inputFile.listFiles();
            if (ArrayUtil.isEmpty(files)){
                return;
            }
            for (File file : files) {
                copyFileRecursive(file,destOutFile);
            }
        }else {
            Path destPath = outFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);
        }

    }
}
