package com.xiaozhang.cli.command;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
 * @author xiaozhang
 * 子命令 获取文件列表
 */
@CommandLine.Command(name = "list",description = "显示文件目录",mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        String parent = new File(projectPath).getParent();
        File file = new File(parent, "generator-acm-template-demo");
        System.out.println("要显示的文件目录"+file.getAbsolutePath());
        //只显示文件
        List<File> files = FileUtil.loopFiles(file);
        for (File file1 : files) {
            System.out.println(file1.getPath());
        }
        //递归读取每一个文件包括文锦夹
//        File[] files = file.listFiles();
//        for (File file1 : files) {
//            displayRecursive(file1);
//        }
    }

    public void displayRecursive(File file){
        if (file.isFile()){
            System.out.println(file.getPath());
            return;
        }
        System.out.println(file.getPath());
        File[] files = file.listFiles();
        if (ArrayUtil.isEmpty(files)){
            return;
        }
        for (File file1 : files) {
           displayRecursive(file1);
        }

    }


}
