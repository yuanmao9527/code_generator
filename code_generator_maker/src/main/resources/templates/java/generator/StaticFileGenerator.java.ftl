package ${basePackage}.generator;
import cn.hutool.core.io.FileUtil;

/**
* 为保证后续代码不出错，尽量不要使用自己写的递归代码。
* */
public class StaticFileGenerator {

    // 直接调用Hutool
    public static void copyFilesByHutool(String inputPath, String outputPath){
        FileUtil.copy(inputPath,outputPath,false);
    }

}
