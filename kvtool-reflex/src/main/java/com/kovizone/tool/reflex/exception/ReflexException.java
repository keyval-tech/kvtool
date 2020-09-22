package com.kovizone.tool.reflex.exception;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class ReflexException extends RuntimeException {

    public ReflexException() {
        super();
    }

    public ReflexException(String message) {
        super(message);
    }

    public ReflexException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflexException(Throwable cause) {
        super(cause);
    }

    protected ReflexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
