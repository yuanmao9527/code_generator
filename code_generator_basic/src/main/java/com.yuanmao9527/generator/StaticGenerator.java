package com.yuanmao9527.generator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class StaticGenerator {
    public static void main(String[] args) {
        System.out.println("Hello World");

        // 获取整个项目的根目录
        String projectPath = System.getProperty("user.dir"); // code_generator_basic 的绝对地址
        File parantFile = new File(projectPath).getParentFile();
        // 输入路径: ACM示例代码模板路径
        String inputPath = new File(parantFile,"code_generator_demo_projects/acm_template").getAbsolutePath();

        // 静态文件启动器
        StaticGenerator staticGenerator = new StaticGenerator();
        // 输出路径：直接输出到项目的根目录
        String outputPath = projectPath;
//        staticGenerator.copyFilesByHutool(inputPath,outputPath);
        staticGenerator.copyFilesByRecursive(inputPath,outputPath);
    }
    // 直接调用Hutool
    public static void copyFilesByHutool(String inputPath, String outputPath){
        FileUtil.copy(inputPath,outputPath,false);
    }

    // 自定义方法
    /*
    * 拷贝文件
    * @param: inputPath 输入
    * @param: outputPath 输出
    * */
    public static void copyFilesByRecursive(String inputPath, String outputPath){
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        try{
            //check
            if(!inputFile.exists()){ // 输入不存在
                System.out.println("File not exit" + inputFile);
            } else if (!outputFile.exists()) {
                System.out.println("File not exit" + outputFile);
            } else {
                copyFileByRecursive(inputFile,outputFile);
            }
        }catch (Exception e){
            System.out.println("复制文件终止");
            e.printStackTrace();
        }

    }

    /*
    * 输入为文件，输出为目录 => 复制文件到目录
    * 输入为目录，输出为目录 => 复制输入目录到输出目录下
    * 输入为文件，输出为文件 => 覆盖
    * @param inputFile 输入File
    * @param outputFile
    * */
    public static void copyFileByRecursive(File inputFile,File outputFile) throws IOException {
        // 是否为目录
        if (inputFile.isDirectory()){
            System.out.println(inputFile.getName());
            File targetOutPath = new File(outputFile,inputFile.getName()); // 目标目录。在输出目录中创建与输入同名目录
            //输入为目录，则在输出目录中建立目标目录
            if(!targetOutPath.exists()){
                targetOutPath.mkdirs();
            }
            //获取输入目录下所有目录与文件
            File[] files = inputFile.listFiles();
            // 若文件夹为空，则直接结束
            if(ArrayUtil.isEmpty(files)){
                return;
            }
            //不为空，递归复制
            for(File file:files){
                copyFileByRecursive(file,targetOutPath);
            }
        }
        else {//为文件，直接复制到输出目录里
            Path targetPath = outputFile.toPath().resolve(inputFile.getPath());
            Files.copy(inputFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
