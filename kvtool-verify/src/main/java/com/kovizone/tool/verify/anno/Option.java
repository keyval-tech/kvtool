package com.kovizone.tool.verify.anno;

import com.kovizone.tool.verify.api.anno.Processor;
import com.kovizone.tool.verify.processor.OptionProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 可选项，不允许除value外的值
 *
 * @author KoviChen
 */
@Processor(OptionProcessor.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface Option {
    String[] value();
}
