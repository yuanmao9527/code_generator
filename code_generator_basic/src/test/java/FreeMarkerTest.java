import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.*;

public class FreeMarkerTest {
    public static void main(String[] args) throws IOException, TemplateException {
        test();
    }
    public static void test() throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        //创建模板对象
        Template template = configuration.getTemplate("myWeb.html.ftl");

        //创建数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("currentYear", 2025);
        List<Map<String, Object>> menuItems = new ArrayList<>();
        Map<String, Object> menuItem1 = new HashMap<>();
        menuItem1.put("url", "https://www.baidu.com");
        menuItem1.put("label", "今日热点");
        Map<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("url", "https://www.bilibili.com");
        menuItem2.put("label", "强烈推荐");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        dataModel.put("menuItems", menuItems);

        // 指定生成的文件名和路径
        Writer out = new FileWriter("myWeb.html");
        //生成文件
        template.process(dataModel, out);

        // 生成文件后别忘了关闭哦
        out.close();


    }
}

