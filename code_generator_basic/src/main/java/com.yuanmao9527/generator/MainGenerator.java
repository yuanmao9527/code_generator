package com.yuanmao9527.generator;

import com.yuanmao9527.models.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        // 创建数据模型
        MainTemplateConfig config = new MainTemplateConfig();
        config.setAuthor("yuanMao9527");
        config.setLoop(false);
        config.setOutputText("多数之和是");
        doGenerate(config);
    }

    public static void doGenerate(Object model) throws IOException, TemplateException {
        // 获取整个项目的根目录
        String projectPath = System.getProperty("user.dir"); // code_generator_basic 的绝对地址
        File parantFile = new File(projectPath).getParentFile();
        // 输入路径: ACM示例代码模板路径
        String inputPath = new File(parantFile,"code_generator_demo_projects/acm_template").getAbsolutePath();
        // 输出路径：直接输出到项目的根目录
        String outputPath = projectPath;

        /*生成静态文件*/
        // 静态文件启动器
        StaticGenerator staticGenerator = new StaticGenerator();
        staticGenerator.copyFilesByRecursive(inputPath,outputPath);

        /*生成动态文件*/
        // 获取整个项目的根目录
        String inputDynamicFilePath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputDynamicFilePath = projectPath + File.separator + "acm_template/src/com/yuanmao9527/acm/MainTemplate.java";//注意地址
        DynamicGenerator dynamicGenerator = new DynamicGenerator();
        dynamicGenerator.doGenerate(inputDynamicFilePath,outputDynamicFilePath,model);
    }

}
