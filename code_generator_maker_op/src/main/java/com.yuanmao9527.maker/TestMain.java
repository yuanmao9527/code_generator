package com.yuanmao9527.maker;
import com.yuanmao9527.maker.generator.file.DynamicGenerator;
import com.yuanmao9527.maker.generator.file.StaticFileGenerator;
import com.yuanmao9527.maker.models.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 测试_gitnore文件还是.gitignore
 */
public class TestMain {

    /**
     * 生成
     *
     * @param model 数据模型
     * @throws TemplateException
     * @throws IOException
     */
    public static void doGenerate(Object model) throws IOException, TemplateException {
        String inputRootPath = "D:/Study/programmes/code_generator/code_generator_demo_projects/acm_template_pro";
        String outputRootPath = "D:\\Study\\programmes\\code_generator\\acm_template_pro";

        String inputPath;
        String outputPath;

        inputPath = new File(inputRootPath, "src/com/yuanmao9527/acm/MainTemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath, "src/com/yuanmao9527/acm/MainTemplate.java").getAbsolutePath();
        DynamicGenerator.doGenerate(inputPath, outputPath, model);

        inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
        StaticFileGenerator.copyFilesByHutool(inputPath, outputPath);

        inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
        outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
        StaticFileGenerator.copyFilesByHutool(inputPath, outputPath);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        DataModel mainTemplateConfig = new DataModel();
        mainTemplateConfig.setAuthor("yupi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerate(mainTemplateConfig);
    }

}

