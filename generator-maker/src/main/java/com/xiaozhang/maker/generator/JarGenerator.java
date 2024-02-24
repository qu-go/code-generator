package com.xiaozhang.maker.generator;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 打jar包的模板
 * @author acal1314
 */
public class JarGenerator {

    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        String otherMavenCommand="mvn clean package -DskipTests=true";
        String winMavenCommand="mvn.cmd clean package -DskipTests=true";
        String command=winMavenCommand;
        //java内部执行命令
        ProcessBuilder processBuilder=new ProcessBuilder(command.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        //打印输出信息
        InputStream inputStream=process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        String line ;
        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("打包结束，输出状态码："+exitCode);


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("F:\\code\\code-generator\\generator-basic");
    }

}
