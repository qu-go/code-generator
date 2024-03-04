package com.xiaozhang.maker.generator.file;

import cn.hutool.core.io.FileUtil;

/**
 * 一个文件拷贝到另一个文件
 * @author acal1314
 */
public class StaticFileGenerator {


    public static void copyFileByHutools(String inputPath ,String outPath){
         FileUtil.copy(inputPath, outPath, false);

    }

}
