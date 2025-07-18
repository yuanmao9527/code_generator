package com.yuanmao9527.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 模板制作器文件配置（文件过滤机制）
 */
@Data
public class TemplateMakerFileConfig {
    private List<FileInfoConfig> files;

    private FileGroupConfig fileGroupConfig;
    @NoArgsConstructor
    @Data
    public static class FileInfoConfig{
        private String path;

        private List<FileFilterConfig> filterConfigList;

    }

    @Data
    public static class FileGroupConfig {

        private String condition;

        private String groupKey;

        private String groupName;
    }
}
