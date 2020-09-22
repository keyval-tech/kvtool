package com.kovizone.tool.verify.anno;

import com.kovizone.tool.verify.api.anno.Processor;
import com.kovizone.tool.verify.processor.VerifyExpressionProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
@Processor(VerifyExpressionProcessor.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface VerifyExpression {

    String[] value();

}
