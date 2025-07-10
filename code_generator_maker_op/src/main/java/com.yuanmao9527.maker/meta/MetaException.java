package com.yuanmao9527.maker.meta;

//异常类 几种处理元信息输入错误导致的异常
public class MetaException extends RuntimeException {

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }
}
