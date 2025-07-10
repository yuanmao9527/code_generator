package ${basePackage}.generator;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

//动态文件生成器
public class DynamicGenerator {

    public static void doGenerate(String inputPath,String outputPath,Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径
        File templateFile = new File(inputPath).getParentFile();
        System.out.println("模板文件所在的路径" + templateFile.getAbsolutePath());
        configuration.setDirectoryForTemplateLoading(templateFile);
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 获取模板名
        String templateName = new File(inputPath).getName();
        // 创建模板对象
        Template template = configuration.getTemplate(templateName);

        // 数据模型 为传入的 model

        // 文件不存在则创建文件和父目录
        if(!FileUtil.exist(outputPath)){
            FileUtil.touch(outputPath);
        }

        // 指定生成的文件名和路径
        Writer out = new FileWriter(outputPath);
        //生成文件
        template.process(model, out);

        // 生成文件后别忘了关闭哦
        out.close();

    }

}
