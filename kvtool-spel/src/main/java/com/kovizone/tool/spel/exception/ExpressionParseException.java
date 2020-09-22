package com.kovizone.tool.spel.exception;

/**
 * 表达式解析异常
 *
 * @author KoviChen
 */
public class ExpressionParseException extends RuntimeException {

    public ExpressionParseException() {
        super();
    }

    public ExpressionParseException(String message) {
        super(message);
    }

    public ExpressionParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionParseException(Throwable cause) {
        super(cause);
    }

    protected ExpressionParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
