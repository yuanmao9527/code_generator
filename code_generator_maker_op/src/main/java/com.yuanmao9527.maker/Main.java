package com.yuanmao9527.maker;

import cn.hutool.extra.template.TemplateException;
import com.yuanmao9527.maker.cli.CommandExecutor;
import com.yuanmao9527.maker.generator.file.StaticFileGenerator;
import com.yuanmao9527.maker.main.MainGenerator;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;


/*
*  Main 方式
* */
public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException, freemarker.template.TemplateException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}
