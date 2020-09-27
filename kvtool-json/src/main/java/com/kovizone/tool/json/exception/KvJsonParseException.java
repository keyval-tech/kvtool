package com.kovizone.tool.json.exception;

/**
 * JSON解析异常
 *
 * @author KoviChen
 * @version 0.0.1 20191018 KoviChen 新建类
 */
public class KvJsonParseException extends Exception {

    public KvJsonParseException(String message) {
        super(message);
    }

    public KvJsonParseException() {
        super();
    }

    public KvJsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public KvJsonParseException(Throwable cause) {
        super(cause);
    }

    protected KvJsonParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
