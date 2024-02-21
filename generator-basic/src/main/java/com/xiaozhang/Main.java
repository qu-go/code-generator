package com.xiaozhang;

import com.xiaozhang.cli.CommandExecutor;

/**
 * @author acal1314
 * 全局调用类
 */
public class Main {

    public static void main(String[] args) {
        args=new String[]{"generate"};
//        args=new String[]{"config"};
//        args=new String[]{"list"};
        CommandExecutor commandExecutor=new CommandExecutor();
        commandExecutor.execute(args);
    }

}
