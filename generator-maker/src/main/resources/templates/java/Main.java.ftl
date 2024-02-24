package ${basePackage};

import ${basePackage}.cli.CommandExecutor;

/**
 * 指令命令执行器启动类
 * @author ${author}
 */
public class Main {

    public static void main(String[] args) {
        CommandExecutor commandExecutor=new CommandExecutor();
        commandExecutor.execute(args);
    }

}
