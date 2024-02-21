package com.xiaozhang.cli.pattern;

/**
 * @author acal1314
 * 客户端
 * 使用遥控器的人
 */
public class Client {

    public static void main(String[] args) {
        Device pc = new Device("pc");
        TurnOffCommand turnOffCommand = new TurnOffCommand(pc);
        RemoteControl remoteControl = new RemoteControl(turnOffCommand);
        remoteControl.pressButton();
    }
}
