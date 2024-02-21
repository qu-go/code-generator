package com.xiaozhang.cli.pattern;

/**
 * @author acal1314
 * 被调用者
 */
public class Device {
    private String name;

    public Device(String name){
        this.name=name;
    }

    //打开

    public void turnOff(){
        System.out.println("关闭设备");
    }
    public void turnOn(){
        System.out.println("打开设备");
    }

}
