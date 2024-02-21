package com.xiaozhang.cli.example;

import cn.hutool.core.util.ArrayUtil;
import picocli.CommandLine;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * 可交互式输入参数
 */
class Login implements Callable<Integer> {
    @CommandLine.Option(names = {"-u", "--user"},arity = "0..1",description = "User name",interactive = true)
    String user;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Passphrase"
        ,arity = "0..1", interactive = true,echo = false)
    char[] password;

    @CommandLine.Option(names = {"-cp","--checkpwd"},arity = "0..1",description = "确认你的密码",interactive = true)
    String checkPassword;
    @Override
    public Integer call() throws Exception {
        System.out.println(password);
        System.out.println("checkPwd:"+checkPassword);
        return 0;
    }

    public static void main(String[] args) {
//        可以判断是否有命令
        if(!ArrayUtil.contains(args,"-u")){
            args=new String[]{"-u","-p","-cp"};
        }
        new CommandLine(new Login()).execute(args);
    }
}