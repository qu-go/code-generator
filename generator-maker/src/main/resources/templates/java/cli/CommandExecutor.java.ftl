package ${basePackage}.cli;

import cn.hutool.core.util.ArrayUtil;
import ${basePackage}.cli.command.ConfigCommand;
import ${basePackage}.cli.command.GeneratorCommand;
import ${basePackage}.cli.command.ListCommand;
import picocli.CommandLine;

/**
 * @author ${author}
 * 假如子命令和执行命令操作
 */
@CommandLine.Command(description = "脚手架",mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {


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
<#--        if (ArrayUtil.contains(args,"generate")){-->
<#--            if (!ArrayUtil.contains(args,"-l")){-->
<#--                args=new String[]{"generate","-l","-a","-o"};-->
<#--            }-->
<#--        }-->
        commandLine.execute(args);
    }



}
