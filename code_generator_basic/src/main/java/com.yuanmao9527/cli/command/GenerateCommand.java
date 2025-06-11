package com.yuanmao9527.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.yuanmao9527.generator.MainGenerator;
import com.yuanmao9527.models.MainTemplateConfig;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "generate", version = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
    // arity 表示可选交互式选项
    @Option(names = {"-l","--loop"}, arity = "0..1",description = "是否循环",interactive = true,echo = true)
    private boolean loop;

    @Option(names = {"-a","--author"}, arity = "0..1",description = "作者",interactive = true,echo = true)
    private String author = "yuanmao";

    @Option(names = {"-b","--brief"}, arity = "0..1",description = "输出文本",interactive = true,echo = true)
    private String outputText = "输出和为"; // 这里的变量名要和 MainTemplateConfig 内的变量名一致才行

    @Override
    public Integer call() throws Exception { // 调用命令行
        MainTemplateConfig config = new MainTemplateConfig();//动态模板配置
        BeanUtil.copyProperties(this,config); // 将命令接受的属性复制给MainTemplateConfig配置对象
        System.out.println("配置信息：" + config);
        MainGenerator.doGenerate(config);//静态模板均在MainGenerator中实现
        return 0;
    }
}
