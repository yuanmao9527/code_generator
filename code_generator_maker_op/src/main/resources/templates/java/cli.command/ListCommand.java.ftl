package ${basePackage}.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

//遍历输出所有要生成的文件列表
@Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    public void run(){ //执行命令
        String projectPath = "${fileConfig.inputRootPath}";
        // 整个项目的根路径
        File parentPath = (new File(projectPath)).getParentFile();
        // 输入路径
        String inputPath = new File(parentPath,"code_generator_demo_projects/acm_template").getAbsolutePath();
        List<File> files = FileUtil.loopFiles(inputPath);
        for(File file : files){
            System.out.println(file);
        }
    }
}