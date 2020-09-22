package com.kovizone.tool.reflex;

import com.kovizone.tool.core.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:kovichen@163.com">KoviChen</a>
 * @version 1.0
 */
public class MethodUtil {

    private final static String FIELD_GET_METHOD_PREFIX = "get";

    private final static String FIELD_IS_METHOD_PREFIX = "is";

    private final static String FIELD_SET_METHOD_PREFIX = "set";

    public static Method getGetterMethod(Class<?> clazz, Field field) {
        Method getMethod = null;
        // 布偶类型且有is前缀的属性
        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            if (field.getName().startsWith(FIELD_IS_METHOD_PREFIX)) {
                getMethod = getMethod(clazz, field.getName());
                if (getMethod == null) {
                    String methodName = FIELD_GET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName().substring(FIELD_IS_METHOD_PREFIX.length())));
                    getMethod = getMethod(clazz, methodName);
                }
            }
            if (getMethod == null) {
                String methodName = FIELD_IS_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName()));
                getMethod = getMethod(clazz, methodName);
            }
        }
        if (getMethod == null) {
            String methodName = FIELD_GET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName()));
            getMethod = getMethod(clazz, methodName);
        }
        return getMethod;
    }

    public static Method getSetterMethod(Class<?> clazz, Field field) {
        Method setMethod = null;
        // 布偶类型且有is前缀的属性
        if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            if (field.getName().startsWith(FIELD_IS_METHOD_PREFIX)) {
                String methodName = FIELD_SET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName().substring(FIELD_IS_METHOD_PREFIX.length())));
                setMethod = getMethod(clazz, methodName, field.getType());
            }
        }
        if (setMethod == null) {
            String methodName = FIELD_SET_METHOD_PREFIX.concat(StringUtil.upperFirstCase(field.getName()));
            setMethod = getMethod(clazz, methodName, field.getType());
        }
        return setMethod;
    }

    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException ignored) {
        }
        return method;
    }

    protected static String getFieldNameParseMethodName(String methodName) {
        String fieldName;
        if (methodName.startsWith(FIELD_GET_METHOD_PREFIX)) {
            fieldName = methodName.substring(FIELD_GET_METHOD_PREFIX.length());
        } else if (methodName.startsWith(FIELD_SET_METHOD_PREFIX)) {
            fieldName = methodName.substring(FIELD_SET_METHOD_PREFIX.length());
        } else if (methodName.startsWith(FIELD_IS_METHOD_PREFIX)) {
            fieldName = methodName.substring(FIELD_IS_METHOD_PREFIX.length());
        } else {
            fieldName = methodName;
        }
        return StringUtil.lowerFirstCase(fieldName);
    }
}
