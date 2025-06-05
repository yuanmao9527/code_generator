package com.yuanmao9527.generator;

import com.yuanmao9527.models.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DynamicGenerator {
//    public static void main(String[] args) throws IOException, TemplateException {
//        // new 出 Configuration 对象，参数为 FreeMarker 版本号
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
//        // 指定模板文件所在的路径
//        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
//        // 设置模板文件使用的字符集
//        configuration.setDefaultEncoding("utf-8");
//
//        // 创建模板对象
//        Template template = configuration.getTemplate("MainTemplate.java.ftl");
//
//        // 创建数据模型
//        MainTemplateConfig config = new MainTemplateConfig();
//        config.setAuthor("yuanMao9527");
//        config.setLoop(false);
//        config.setOutputText("多数之和是");
//
//        // 指定生成的文件名和路径
//        Writer out = new FileWriter("MainTemplate.java");
//        //生成文件
//        template.process(config, out);
//
//        // 生成文件后别忘了关闭哦
//        out.close();
//
//    }


    public static void main(String[] args) throws IOException, TemplateException {
        // 获取整个项目的根目录
        String projectPath = System.getProperty("user.dir"); // code_generator_basic 的绝对地址
        String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";//注意地址要写全，和上面的不一样
        String outputPath = projectPath + File.separator + "MainTemplate.java";
        // 创建数据模型
        MainTemplateConfig config = new MainTemplateConfig();
        config.setAuthor("yuanMao9527");
        config.setLoop(false);
        config.setOutputText("多数之和是");
        doGenerate(inputPath, outputPath, config);
    }

    public static void doGenerate(String inputPath,String outputPath,Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径
        File templateFile = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateFile);
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 获取模板名
        String templateName = new File(inputPath).getName();
        // 创建模板对象
        Template template = configuration.getTemplate(templateName);

        // 数据模型 为传入的 model

        // 指定生成的文件名和路径
        Writer out = new FileWriter(outputPath);
        //生成文件
        template.process(model, out);

        // 生成文件后别忘了关闭哦
        out.close();

    }

}
