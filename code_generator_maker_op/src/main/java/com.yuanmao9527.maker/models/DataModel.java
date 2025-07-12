package com.yuanmao9527.maker.models;

public class DataModel {
    /*
     * 是否生成循环
     * */
    public boolean loop;

    /*
     * 作者注释
     * */
    public String author ;

    /*
     * 输出信息
     * */
    public String outputText ;

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
