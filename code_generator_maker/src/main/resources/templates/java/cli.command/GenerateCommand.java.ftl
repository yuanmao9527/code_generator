package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.FileGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {
    <#list modelConfig.models as modelInfo>
    @Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}",</#if><#if modelInfo.abbr??>"--${modelInfo.fieldName}"</#if>}, arity = "0..1",<#if modelInfo.description??>description ="${modelInfo.description}",</#if>interactive = true,echo = true)
    private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c}</#if>;
    </#list>

    @Override
    public Integer call() throws Exception { // 调用命令行
        DataModel config = new DataModel();//动态模板配置
        BeanUtil.copyProperties(this,config); // 将命令接受的属性复制给MainTemplateConfig配置对象
        System.out.println("配置信息：" + config);
        FileGenerator.doGenerate(config);//静态模板均在MainGenerator中实现
        return 0;
    }
}