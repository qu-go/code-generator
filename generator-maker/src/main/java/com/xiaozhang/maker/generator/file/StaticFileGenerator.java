package com.xiaozhang.maker.generator.file;

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
public class StaticFileGenerator {


    public static void copyFileByHutools(String inputPath ,String outPath){
         FileUtil.copy(inputPath, outPath, false);

    }

}
