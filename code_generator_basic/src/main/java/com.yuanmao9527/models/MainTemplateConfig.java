package com.yuanmao9527.models;

import lombok.Data;

import java.util.Objects;

/*
* 动态模板配置
* */
//@Data
public class MainTemplateConfig {
    /*
    * 是否生成循环
    * */
    private boolean loop;

    /*
    * 作者注释
    * */
    private String author ;

    /*
    * 输出信息
    * */
    private String outputText ;

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public boolean isLoop() {
        return loop;
    }

    public String getAuthor() {
        return author;
    }

    public String getOutputText() {
        return outputText;
    }

}
