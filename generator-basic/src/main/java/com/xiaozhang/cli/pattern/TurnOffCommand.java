package com.xiaozhang.cli.pattern;

/**
 * @author 校长
 * 具体命令
 */
public class TurnOffCommand implements Command{
    private Device device;

    public TurnOffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}
