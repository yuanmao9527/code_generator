package com.yuanmao9527.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.yuanmao9527.maker.meta.enums.FileGenerateTypeEnum;
import com.yuanmao9527.maker.meta.enums.FileTypeEnum;
import com.yuanmao9527.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 元信息校验 与 填充默认值
 */
public class MetaValidator {

    public static void doValidAndFill(Meta meta) {
        
        validAndFillMetaRoot(meta);

        // fileConfig 校验和默认值
        validAndFillFileConfig(meta);

        // modelConfig 校验和默认值
        validAndFillModelConfig(meta);

    }

    private static void validAndFillModelConfig(Meta meta) {
        Meta.ModelConfig modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        List<Meta.ModelConfig.ModelInfo> modelInfoList = modelConfig.getModels();
        if (CollectionUtil.isNotEmpty(modelInfoList)) {
            for (Meta.ModelConfig.ModelInfo modelInfo : modelInfoList) {
//                System.out.println(modelInfo.toString());
                // 为 group，不校验
                String groupKey = modelInfo.getGroupKey();
                if (StrUtil.isNotEmpty(groupKey)) {
                    // 生成中间参数
                    List<Meta.ModelConfig.ModelInfo> subModelInfoList = modelInfo.getModels();
                    String allArgsStr = modelInfo.getModels().stream()
                            .map(subModelInfo -> String.format("\"--%s\"", subModelInfo.getFieldName()))
                            .collect(Collectors.joining(", "));
                    modelInfo.setAllArgsStr(allArgsStr);
                    continue;
                }
                // 输出路径默认值
                String fieldName = modelInfo.getFieldName();
                if (StrUtil.isBlank(fieldName)) {
                    throw new MetaException("未填写 fieldName");
                }

                String modelInfoType = modelInfo.getType();
                if (StrUtil.isEmpty(modelInfoType)) {
                    modelInfo.setType(ModelTypeEnum.STRING.getValue());
                }
            }
        }
    }

    private static void validAndFillFileConfig(Meta meta) {
        Meta.FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        // sourceRootPath：必填
        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)) {
            throw new MetaException("未填写 sourceRootPath");
        }
        // inputRootPath：.source + sourceRootPath 的最后一个层级路径
        String inputRootPath = fileConfig.getInputRootPath();
        String defaultInputRootPath = ".source" + File.separator + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
        if (StrUtil.isEmpty(inputRootPath)) {
            fileConfig.setInputRootPath(defaultInputRootPath);
        }
        // outputRootPath：默认为当前路径下的 generated
        String outputRootPath = fileConfig.getOutputRootPath();
        String defaultOutputRootPath = "generated";
        if (StrUtil.isEmpty(outputRootPath)) {
            fileConfig.setOutputRootPath(defaultOutputRootPath);
        }
        String fileConfigType = fileConfig.getType();
        String defaultType = FileTypeEnum.DIR.getValue();
        if (StrUtil.isEmpty(fileConfigType)) {
            fileConfig.setType(defaultType);
        }

        // fileInfo 默认值
        List<Meta.FileConfig.FileInfo> fileInfoList = fileConfig.getFiles();
        if (CollectionUtil.isNotEmpty(fileInfoList)) {
            for (Meta.FileConfig.FileInfo fileInfo : fileInfoList) {
                // type：默认 inputPath 有文件后缀（如 .java）为 file，否则为 dir
                String type = fileInfo.getType();
                if(FileTypeEnum.GROUP.getValue().equals(type)){ // group不填写文件输入输出路径
                    continue;
                }
                // inputPath: 必填
                String inputPath = fileInfo.getInputPath();
                if (StrUtil.isBlank(inputPath)) {
                    throw new MetaException("未填写 inputPath");
                }
                // outputPath: 默认等于 inputPath
                String outputPath = fileInfo.getOutputPath();
                if (StrUtil.isEmpty(outputPath)) {
                    fileInfo.setOutputPath(inputPath);
                }

                if (StrUtil.isBlank(type)) {
                    // 无文件后缀
                    if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))) {
                        fileInfo.setType(FileTypeEnum.DIR.getValue());
                    } else {
                        fileInfo.setType(FileTypeEnum.FILE.getValue());
                    }
                }
                // generateType：如果文件结尾不为 Ftl，generateType 默认为 static，否则为 dynamic
                String generateType = fileInfo.getGenerateType();
                if (StrUtil.isBlank(generateType)) {
                    // 为动态模板
                    if (inputPath.endsWith(".ftl")) {
                        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                    } else {
                        fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                    }
                }
            }
        }
    }

    public static void validAndFillMetaRoot(Meta meta) {
        // 基础信息校验和默认值
        // emptyToDefault	仅 null 和 ""
        // blankToDefault	null、"" 和纯空白字符串（如 " "）
        String name = StrUtil.blankToDefault(meta.getName(),"my-generator");
        String description = StrUtil.emptyToDefault(meta.getDescription(),"我的模板代码生成器");
        String author = StrUtil.emptyToDefault(meta.getAuthor(),"yupi");
        String basePackage = StrUtil.blankToDefault(meta.getBasePackage(),"com.yupi");
        String version = StrUtil.emptyToDefault(meta.getVersion(),"1.0");
        String createTime = StrUtil.emptyToDefault(meta.getCreateTime(),DateUtil.now());

        meta.setName(name);
        meta.setDescription(description);
        meta.setAuthor(author);
        meta.setBasePackage(basePackage);
        meta.setVersion(version);
        meta.setCreateTime(createTime);
    }

}
