package com.yuanmao9527.maker.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.yuanmao9527.maker.models.DataModel;
import picocli.CommandLine.Command;

import java.lang.reflect.Field;

// 利用java反射机制
@Command(name = "config", description = "查看参数信息", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable {
    public void run(){
        System.out.println("查看参数信息");
        Field[] fileds = ReflectUtil.getFields(DataModel.class);
        // 遍历并打印变量信息
        for (Field field : fileds) {
            System.out.println("字段名称" + field.getName());
            System.out.println("字段类型" + field.getType());
            System.out.println("-------");
        }
    }
}
