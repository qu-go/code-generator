package com.xiaozhang.cli.pattern;

/**
 * @author acal1314
 * 调用者
 * 相当于遥控器
 */
public class RemoteControl {

    private Command command;

    public RemoteControl(Command command) {
        this.command = command;
    }

    public void pressButton(){
        command.execute();
    }
}
