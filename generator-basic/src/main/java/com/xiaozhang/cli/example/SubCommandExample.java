package com.xiaozhang.cli.example;
import picocli.CommandLine;
import picocli.CommandLine.Command;
/**
 * @author acal1314
 * 子命令操作
 */
@Command(name ="main",version = "v1.0.1",mixinStandardHelpOptions = true)
public class SubCommandExample implements Runnable{

    @Override
    public void run() {
        System.out.println("总命令");

    }
    @Command(name = "add",description = "增加",mixinStandardHelpOptions = true)
    static class Add implements Runnable{

        @Override
        public void run() {

        }
    }
    @Command(name = "delete",description = "增加",mixinStandardHelpOptions = true)
    static class Delete implements Runnable{

        @Override
        public void run() {

        }
    }

    public static void main(String[] args) {
        new CommandLine(new SubCommandExample())
            .addSubcommand(new Add())
            .addSubcommand(new Delete())
            .execute(args);
    }
}
