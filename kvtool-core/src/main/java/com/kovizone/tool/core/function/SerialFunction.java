package com.kovizone.tool.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
@FunctionalInterface
public interface SerialFunction<T, R> extends Function<T, R>, Serializable {
}
