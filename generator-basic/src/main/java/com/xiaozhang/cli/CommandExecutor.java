package com.xiaozhang.cli;

import cn.hutool.core.util.ArrayUtil;
import com.xiaozhang.cli.command.CommandInterface;
import com.xiaozhang.cli.command.ConfigCommand;
import com.xiaozhang.cli.command.GeneratorCommand;
import com.xiaozhang.cli.command.ListCommand;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;

/**
 * @author acal1314
 * 假如子命令和执行命令操作
 */
@CommandLine.Command(description = "脚手架",mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {


    private CommandInterface commandInterface;
    private final CommandLine commandLine;
    {
        commandLine=new CommandLine(this)
            .addSubcommand(ConfigCommand.class)
            .addSubcommand(GeneratorCommand.class)
            .addSubcommand(ListCommand.class);

    }



    @Override
    public void run() {
        System.out.println("请输入具体操作命令");
    }

    public void execute(String[] args){
        //commandInterface.check(args);
        //指令检查
        if (ArrayUtil.contains(args,"generate")){
            if (!ArrayUtil.contains(args,"-l")){
                args=new String[]{"generate","-l","-a","-o"};
            }
        }
        commandLine.execute(args);
    }



}
