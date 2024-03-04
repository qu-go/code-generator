package com.xiaozhang.maker.generator;

import cn.hutool.core.io.FileUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * @author acal1314
 * 脚本生成模板
 */
public class ScriptGenerator {

    public static void doGenerate(String outputPath,String jarPath){
        //linux
        StringBuilder sb=new StringBuilder();

        sb.append("#!/bin/bash").append("\n");
        sb.append(String.format("java -jar %s \"$@ \"",jarPath));
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8),outputPath);
        //添加权限
        try {
            Set<PosixFilePermission> permission = PosixFilePermissions.fromString("rwxrwxrwx");
            Files.setPosixFilePermissions(Paths.get(outputPath),permission);
        } catch (Exception e) {

        }

        //windaos
        sb=new StringBuilder();
        sb.append("@echo off").append("\n");
        sb.append(String.format("java -jar %s %%*", jarPath));
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8),outputPath+".bat");

    }

//    public static void main(String[] args) {
//        String outPath=System.getProperty("user.dir")+ File.separator+"generator";
//        doGenerate(outPath,"");
//    }

}
