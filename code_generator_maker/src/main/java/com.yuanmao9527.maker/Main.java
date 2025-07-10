package com.yuanmao9527.maker;

import com.yuanmao9527.maker.cli.CommandExecutor;
import com.yuanmao9527.maker.generator.file.StaticFileGenerator;
import picocli.CommandLine;

import java.io.File;


/*
*  Main 方式
* */
public class Main {
    public static void main(String[] args) {
        args = new String[]{"generate","-l","-a","-b"};
//        args = new String[]{"config"};
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
//    public static void main(String[] args) {
//        System.out.println("Hello World");
//
//        // 获取整个项目的根目录
//        String projectPath = System.getProperty("user.dir"); // code_generator_basic 的绝对地址
//        File parantFile = new File(projectPath).getParentFile();
//        // 输入路径: ACM示例代码模板路径
//        String inputPath = new File(parantFile,"code_generator_demo_projects/acm_template").getAbsolutePath();
//
//        // 静态文件启动器
//        StaticGenerator staticGenerator = new StaticGenerator();
//        // 输出路径：直接输出到项目的根目录
//        String outputPath = projectPath;
////        staticGenerator.copyFilesByHutool(inputPath,outputPath);
//        staticGenerator.copyFilesByRecursive(inputPath,outputPath);
//    }
}
