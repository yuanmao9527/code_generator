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

/**
 * 为子类，继承模板类
 */
public class MainGenerator extends GenerateTemplate{

    // 可以进行覆盖, 来取消某个功能
    @Override
    protected void buildDist(String outputPath, String jarPath, String shellOutputFilePath, String sourceCopyDestPath){
        System.out.println();
    }

}
