package com.kovizone.tool.reflex;

import com.kovizone.tool.core.function.SerialFunction;
import com.kovizone.tool.reflex.exception.ReflexException;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 属性工具
 *
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class FieldUtil {

    public static <T, R> SerializedLambda serializedLambda(SerialFunction<T, R> func) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 调用writeReplace
        Method writeReplace = func.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        return (SerializedLambda) writeReplace.invoke(func);
    }

    public static String getFieldName(SerialFunction<?, ?> func) {
        String fieldName;
        try {
            SerializedLambda serializedLambda = serializedLambda(func);
            fieldName = MethodUtil.getFieldNameParseMethodName(serializedLambda.getImplMethodName());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new ReflexException(e);
        }
        return fieldName;
    }

    public static Field getField(Class<?> clazz, SerialFunction<?, ?> func) {
        return getField(clazz, getFieldName(func));
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new ReflexException("找不到属性：" + fieldName);
        }
    }


}
