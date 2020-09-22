package com.kovizone.tool.verify.anno;

import com.kovizone.tool.verify.api.anno.Processor;
import com.kovizone.tool.verify.processor.ConstraintProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字符串约束<BR/>
 * 是否允许中文、英文、数字、特殊符号或直接使用正则表达式<BR/>
 * 与NumString注解冲突
 *
 * @author KoviChen (kovichen@163.com)
 * @version 0.0.1 20191113 KoviChen 新建类
 */
@Processor(ConstraintProcessor.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface Constraint {

    String RegExp() default "";

    boolean specialSymbols() default true;

    boolean number() default true;

    boolean chinese() default true;

    boolean english() default true;

}
