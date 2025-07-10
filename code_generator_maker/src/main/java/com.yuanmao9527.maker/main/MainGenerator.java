package com.yuanmao9527.maker.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.yuanmao9527.maker.generator.file.DynamicGenerator;
import com.yuanmao9527.maker.generator.file.JarGenerator;
import com.yuanmao9527.maker.generator.file.ScriptGenerator;
import com.yuanmao9527.maker.meta.Meta;
import com.yuanmao9527.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        /*1、测试是否正常获取元信息*/
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        /*2、测试是否正确生成数据模型文件*/
        String projectPath = System.getProperty("user.dir");//项目根路径
        //输出根路径
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }

        //读取resources 目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();//自动指定resources文件夹根路径
//        System.out.println("inputResourcePath：" + inputResourcePath);

        //Java 包基础路径
        String outputBasePath = meta.getBasePackage();//
        String outputBasePackagePath = StrUtil.join("/",StrUtil.split(outputBasePath,"."));//将"."换为"/"
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;//

        String inputFilePath;
        String outputFilePath;

        //model.DataModel
        //ftl文件路径
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";//小心拼写templates 加s
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";//输出路径+自定义输出文件名
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta); // 动态文件生成

        //cli.command.ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli.command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //cli.command.GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli.command/GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //cli.command.ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli.command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //cli.command.CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli.command/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/CommandExecutor.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //Main
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/Main.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //generator.DynamicGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/DynamicGenerator.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //generator.FileGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/FileGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/FileGenerator.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //generator.StaticFileGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticFileGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/StaticFileGenerator.java";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //pom.xml
        inputFilePath = inputResourcePath + File.separator + "/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicGenerator.doGenerate(inputFilePath,outputFilePath,meta);

        //构建jar包
        JarGenerator.doGenerate(outputPath);

        //封装脚本
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);

    }
}
