package com.kovizone.tool.verify.anno;

import com.kovizone.tool.verify.api.anno.Processor;
import com.kovizone.tool.verify.processor.NumStringProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记字符串能转为数字<BR>
 *
 * @author KoviChen (kovichen@163.com)
 * @version 0.0.1 20200214 KoviChen 新建类
 */
@Processor(NumStringProcessor.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface NumString {

    /**
     * 是否有小数点
     */
    boolean point() default true;

    /**
     * 是否有符号（是否允许负数）
     */
    boolean symbol() default true;

}
