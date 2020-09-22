package com.kovizone.tool.verify.anno;

import com.kovizone.tool.verify.api.anno.Processor;
import com.kovizone.tool.verify.processor.VerifyListProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记属性为自定义对象<BR/>
 * 将会单独验证对象<BR/>
 *
 * @author KoviChen (kovichen@163.com)
 * @version 0.0.1 20200214 KoviChen 新建类
 */
@Processor(VerifyListProcessor.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface VerifyList {
}