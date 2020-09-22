package com.kovizone.tool.verify.api.processor;

import com.kovizone.tool.verify.exception.VerifyException;

import java.lang.annotation.Annotation;

/**
 * 验证处理器
 *
 * @author KoviChen
 */
public interface VerifyProcessor<A extends Annotation> {

    /**
     * 验证
     *
     * @param annotation 注解
     * @param fieldName  属性名
     * @param value      属性值
     * @return boolean 若为true，则进入后续的校验，否则中止校验
     * @throws VerifyException 验证失败信息
     */
    void verify(A annotation, String fieldName, Object value) throws VerifyException;

}