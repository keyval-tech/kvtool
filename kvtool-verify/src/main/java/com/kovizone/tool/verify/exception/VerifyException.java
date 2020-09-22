package com.kovizone.tool.verify.exception;

/**
 * 校验异常
 *
 * @author KoviChen
 * @version 0.0.1 20191206 KoviChen 新建类
 */
public class VerifyException extends Exception {

    public VerifyException(String fieldName, String verifyFailedMessage) {
        super(fieldName.concat(": ").concat(verifyFailedMessage));
    }

    public VerifyException(String fieldName, String verifyFailedMessage, Object... args) {
        super(fieldName.concat(": ").concat(String.format(verifyFailedMessage.replace("{}", "%s"), args)));
    }

}
