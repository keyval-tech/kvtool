package com.kovizone.tool.verify.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记字符串验证可为空
 * 
 * @author KoviChen (kovichen@163.com)
 * @version 0.0.1 20191113 KoviChen 新建类
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Nullable {

}
